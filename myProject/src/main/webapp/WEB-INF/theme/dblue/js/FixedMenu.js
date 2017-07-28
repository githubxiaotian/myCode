//容器集合
var FixedMenu = function(containers, options) {
	this._timerContainer = null;//容器定时器
	this._timerMenu = null;//菜单定时器
	this._frag = document.createDocumentFragment();//碎片对象，保存菜单元素
	this._menus = {};//菜单对象
	
	this._containers = [];//容器集合
	
	this._setOptions(options);
	var opt = this.options;
	
	this._custommenu = opt.menu;
	
	this.css = opt.css;
	this.hover = opt.hover;
	this.active = opt.active;
	this.tag = opt.tag;
	this.html = opt.html;
	this.relContainer = opt.relContainer;
	this.relative = opt.relative;
	this.attribute = opt.attribute;
	this.property = opt.property;
	
	this.onBeforeShow = opt.onBeforeShow;
	
	this.delay = parseInt(opt.delay) || 0;
	
	//修正自定义容器
	$$A.forEach($$A.isArray(containers) ? containers : [containers], function(o, i){
		//自定义容器 id:定位元素 menu:插入菜单元素
		var pos, menu;
		if ( o.id ) {
			pos = o.id; menu = o.menu ? o.menu : pos;
		} else {
			pos = menu = o;
		};
		pos = $$(pos); menu = $$(menu);
		//容器对象 pos:定位元素 menu:插入菜单元素
		pos && menu && this._iniContainer( i, { "pos": pos, "menu": menu } );
	}, this);
	
	//初始化程序
	this._iniMenu();
};
FixedMenu.prototype = {
  //设置默认属性
  _setOptions: function(options) {
	this.options = {//默认值
		menu:			[],//自定义菜单集合
		delay:			200,//延迟值(微秒)
		tag:			"div",//默认生成标签
		css:			undefined,//默认样式
		hover:			undefined,//触发菜单样式
		active:			undefined,//显示下级菜单时显示样式
		html:			"",//菜单内容
		relContainer:	false,//是否相对容器定位（否则相对菜单）
		relative:		{ align: "clientleft", vAlign: "bottom" },//定位对象
		attribute:		{},//自定义attribute属性
		property:		{},//自定义property属性
		onBeforeShow:	function(){}//菜单显示时执行
	};
	$$.extend( this.options, options || {} );
  },
  //程序初始化
  _iniMenu: function() {
	this.hide();//隐藏菜单
	this._buildMenu();//生成菜单对象
	this._forEachContainer(this._resetContainer);//重置容器属性
	this._insertMenu(0, 0);//显示菜单
  },
  //根据自定义菜单对象生成程序菜单对象
  _buildMenu: function() {
	//清除旧菜单dom(包括自定义的)
	this._forEachMenu(function(o){
		var elem = o._elem;
		if ( elem ) {
			//防止dom内存泄漏
			$$E.removeEvent( elem, "mouseover", o._event );
			elem.parentNode.removeChild(elem);
			o._elem = o.elem = null;
		};
	});
	//设置菜单默认值
	var options = {
		id:				0,//id
		rank:			0,//排序
		elem:			"",//自定义元素
		tag:			this.tag,
		css:			this.css,
		hover:			this.hover,
		active:			this.active,
		html:			this.html,
		relContainer:	!!this.relContainer,
		relative:		this.relative,
		attribute:		this.attribute,
		property:		this.property
	};
	//先定义"0"顶级菜单
	this._menus = { "0": { "_children": [] } };
	//整理自定义菜单并插入到程序菜单对象
	$$A.forEach(this._custommenu, function(o) {
		//生成菜单对象(由于包含对象，要用深度扩展)
		var menu = $$.deepextend( $$.deepextend( {}, options ), o || {} );
		//去掉相同id菜单，同时排除了id为"0"的菜单
		if ( !!this._menus[ menu.id ] ) { return; };
		//重置属性
		menu._children = []; menu._index = -1;
		this._menus[menu.id] = menu;
	}, this);
	//建立树形结构
	this._forEachMenu(function( o, id, menus ) {
		if ( "0" === id ) { return; };//顶级没有父级菜单
		var parent = this._menus[o.parent];
		//父级菜单不存在或者父级是自己的话，当成一级菜单
		if ( !parent || parent === o ) { parent = menus[o.parent = "0"]; };
		//插入到父级菜单对象的_children中
		parent._children.push(o);
	});
	//整理菜单对象
	this._forEachMenu(function(o) {
		//如果有自定义元素的话先放到碎片文档中
		!!o.elem && ( o.elem = $$(o.elem) ) && this._frag.appendChild(o.elem);
		//修正样式,优先使用自定义元素的class
		if ( !!o.elem && o.elem.className ) {
			o.css = o.elem.className;
		} else if ( o.css === undefined ) { o.css = ""; };
		if ( o.hover === undefined ) { o.hover = o.css; };
		if ( o.active === undefined ) { o.active = o.hover; };
		//对菜单对象的_children集合排序(先按rank再按id排序)
		o._children.sort(function( x, y ) { return x.rank - y.rank || x.id - y.id; });
	});
  },
  //插入菜单
  _insertMenu: function(index, parent) {
	var container = this._containers[index];
	//如果是同一个父级菜单不用重复插入
	if ( container._parent === parent ) { return; };
	container._parent = parent;
	//把原有容器内菜单移到碎片对象中
	$$A.forEach( container._menus, function(o) { o._elem && this._frag.appendChild(o._elem); }, this );
	//重置子菜单对象集合
	container._menus = [];
	//把从父级菜单元素的子菜单对象集合获取的元素插入到容器
	$$A.forEach(this._menus[parent]._children, function( menu, i ){
		this._checkMenu( menu, index );//检查菜单
		container._menus.push(menu);//加入到容器的子菜单集合，方便调用
		container.menu.appendChild(menu._elem);//菜单元素插入到容器
	}, this);
  },
  //检查菜单
  _checkMenu: function(menu, index) {
	//索引保存到菜单对象属性中，方便调用
	menu._index = index;
	//如果菜单对象没有元素
	if ( !menu._elem ) {
		var elem = menu.elem;
		//如果没有自定义元素的话创建一个
		if ( !elem ) { elem = document.createElement(menu.tag); elem.innerHTML = menu.html; };
		//设置property
		$$.extend( elem, menu.property );
		//设置attribute
		var attribute = menu.attribute;
		for (var att in attribute) { elem.setAttribute( att, attribute[att] ); };
		//设置样式
		elem.className = menu.css;
		//设置事件
		menu._event = $$F.bindAsEventListener( this._hoverMenu, this, menu );//用于清除事件
		$$E.addEvent( elem, "mouseover", menu._event );
		//保存到菜单对象
		menu._elem = elem;
	};
  },
  //触发菜单
  _hoverMenu: function(e, menu) {
	var elem = menu._elem;
	//如果是内部元素触发直接返回
	if ( $$D.contains( elem, e.relatedTarget ) || elem === e.relatedTarget ) { return; };
	clearTimeout(this._timerMenu);
	//可能在多个容器间移动，所以全部容器都重新设置样式
	this._forEachContainer(function(o, i){
		if ( o.pos.visibility === "hidden" ) { return; };
		this._resetCss(o);
		//设置当前菜单为active样式
		var menu = o._active;
		if ( menu ) { menu._elem.className = menu.active; };
	});
	//设置当前菜单为触发样式
	if ( this._containers[menu._index]._active !== menu ) { elem.className = menu.hover; };
	//触发显示菜单
	this._timerMenu = setTimeout( $$F.bind( this._showMenu, this, menu ), this.delay );
  },
  //显示菜单
  _showMenu: function(menu) {
	var index = menu._index, container = this._containers[index], child = !!menu._children.length;
	//隐藏不需要的容器
	this._forEachContainer( function(o, i) { i > index && this._hideContainer(o); } );
	//重置当前容器_active
	container._active = null;
	//如果有子级菜单
	if ( child ) {
		//设置当前容器_active
		container._active = menu;
		//显示下一级容器
		index++;//设置索引
		this._checkContainer(index);//检查容器
		this._insertMenu(index, menu.id);//插入菜单
		this._showContainer(menu);//显示容器
	};
	//重置当前容器的css
	this._resetCss(container);
	//设置当前菜单样式
	menu._elem.className = child ? menu.active : menu.hover;
  },
  //初始化容器(索引, 容器元素)
  _iniContainer: function(index, container) {
	var oContainer = container.pos;
	//重置属性
	this._resetContainer(container);
	//添加事件
	$$E.addEvent(oContainer, "mouseover", $$F.bind(function(){ clearTimeout(this._timerContainer); }, this));
	$$E.addEvent(oContainer, "mouseout", $$F.bindAsEventListener(function(e){
		//先判断是否移出到所有容器之外
		var elem = e.relatedTarget,
			isOut = $$A.every(this._containers, function(o){ return  o.pos == elem || !($$D.contains(o.pos, elem)); });
		if ( isOut ) {
			//清除定时器并隐藏
			clearTimeout(this._timerContainer); clearTimeout(this._timerMenu);
			this._timerContainer = setTimeout( $$F.bind( this.hide, this ), this.delay );
		};
	}, this));
	//除了第一个容器外设置浮动样式
	if ( index ) {
		$$D.setStyle(container.pos, {
			position: "absolute", display: "block", margin: 0,
			zIndex: this._containers[index - 1].pos.style.zIndex + 1//要后面的覆盖前面的
		});
	};
	//ie6处理select
	if ( $$B.ie6 ) {
		var iframe = document.createElement("<iframe style='position:absolute;filter:alpha(opacity=0);display:none;'>");
		document.body.insertBefore(iframe, document.body.childNodes[0]);
		container._iframe = iframe;
	};
	//记录索引，方便调用
	container._index = index;
	//插入到容器集合
	this._containers[index] = container;
  },
  //检查容器
  _checkContainer: function(index) {
	if ( index > 0 && !this._containers[index] ) {
		//如果容器不存在，根据前一个容器复制成新容器，第一个容器必须自定义
		var pre = this._containers[index - 1].pos
		//用了新的添加事件方式，没有ie的cloneNode的bug
			,container = pre.parentNode.insertBefore( pre.cloneNode(false), pre );
		//清除id防止冲突
		container.id = "";
		//初始化容器
		this._iniContainer( index, { "pos": container, "menu": container } );
	};
  },
  //显示容器
  _showContainer: function(menu) {
	var index = menu._index
		,container = this._containers[index + 1].pos
		,elem = menu.relContainer ? this._containers[index].pos : menu._elem
		,pos = RelativePosition( elem, container, menu.relative );
	//执行显示前事件
	this.onBeforeShow(container, menu);
	//定位并显示容器
	$$D.setStyle(container, {
		left: pos.Left + "px", top: pos.Top + "px", visibility: "visible"
	});
	//ie6处理select
	if ( $$B.ie6 ) {
		$$D.setStyle(this._containers[index + 1]._iframe, {
			width: container.offsetWidth + "px",
			height: container.offsetHeight + "px",
			left: pos.Left + "px", top: pos.Top + "px",
			display: ""
		});
	};
  },
  //隐藏容器
  _hideContainer: function(container) {
	//设置隐藏
	$$D.setStyle( container.pos, { left: "-9999px", top: "-9999px", visibility: "hidden" } );
	//重置上一个菜单的触发菜单对象
	this._containers[container._index - 1]._active = null;
	//ie6处理select
	if ( $$B.ie6 ) { container._iframe.style.display = "none"; };
  },
  //重置容器对象属性
  _resetContainer: function(container) {
	container._active = null;//重置触发菜单
	container._menus = [];//重置子菜单对象集合
	container._parent = -1;//重置父级菜单id
  },
  //隐藏菜单
  hide: function() {
	this._forEachContainer(function(o, i){
		if ( i === 0 ) {
			//如果是第一个重设样式和_active
			this._resetCss(o);
		} else {//隐藏容器
			this._hideContainer(o);
		};
	});
  },
  //重设容器菜单样式
  _resetCss: function(container) {
	$$A.forEach( container._menus, function(o, i){ o._elem.className = o.css; }, this );
  },
  //历遍菜单对象集合
  _forEachMenu: function(callback) {
	for ( var id in this._menus ) { callback.call( this, this._menus[id], id, this._menus ); };
  },
  //历遍容器对象集合
  _forEachContainer: function(callback) {
	$$A.forEach( this._containers, callback, this );
  },
  //添加自定义菜单
  add: function(menu) {
	this._custommenu = this._custommenu.concat(menu);
	this._iniMenu();
  },
  //修改自定义菜单
  edit: function(menu) {
	$$A.forEach( $$A.isArray( menu ) ? menu : [ menu ], function(o){
		//如果对应id的菜单存在
		if ( o.id && this._menus[o.id] ) {
			//从自定义菜单中找出对应菜单,并修改
			$$A.every( this._custommenu, function(m, i){
				if ( m.id === o.id ) {
					this._custommenu[i] = $$.deepextend( m, o ); return false;
				};
				return true;
				//用every可以跳出循环
			}, this );
		};
	}, this );
	this._iniMenu();
  },
  //删除自定义菜单
  del: function() {
	var ids = Array.prototype.slice.call(arguments);
	this._custommenu = $$A.filter( this._custommenu, function(o){
		return $$A.indexOf(ids, o.id) === -1;
	});
	this._iniMenu();
  }
};