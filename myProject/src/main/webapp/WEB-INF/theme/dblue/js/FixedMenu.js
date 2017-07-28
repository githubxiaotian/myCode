//��������
var FixedMenu = function(containers, options) {
	this._timerContainer = null;//������ʱ��
	this._timerMenu = null;//�˵���ʱ��
	this._frag = document.createDocumentFragment();//��Ƭ���󣬱���˵�Ԫ��
	this._menus = {};//�˵�����
	
	this._containers = [];//��������
	
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
	
	//�����Զ�������
	$$A.forEach($$A.isArray(containers) ? containers : [containers], function(o, i){
		//�Զ������� id:��λԪ�� menu:����˵�Ԫ��
		var pos, menu;
		if ( o.id ) {
			pos = o.id; menu = o.menu ? o.menu : pos;
		} else {
			pos = menu = o;
		};
		pos = $$(pos); menu = $$(menu);
		//�������� pos:��λԪ�� menu:����˵�Ԫ��
		pos && menu && this._iniContainer( i, { "pos": pos, "menu": menu } );
	}, this);
	
	//��ʼ������
	this._iniMenu();
};
FixedMenu.prototype = {
  //����Ĭ������
  _setOptions: function(options) {
	this.options = {//Ĭ��ֵ
		menu:			[],//�Զ���˵�����
		delay:			200,//�ӳ�ֵ(΢��)
		tag:			"div",//Ĭ�����ɱ�ǩ
		css:			undefined,//Ĭ����ʽ
		hover:			undefined,//�����˵���ʽ
		active:			undefined,//��ʾ�¼��˵�ʱ��ʾ��ʽ
		html:			"",//�˵�����
		relContainer:	false,//�Ƿ����������λ��������Բ˵���
		relative:		{ align: "clientleft", vAlign: "bottom" },//��λ����
		attribute:		{},//�Զ���attribute����
		property:		{},//�Զ���property����
		onBeforeShow:	function(){}//�˵���ʾʱִ��
	};
	$$.extend( this.options, options || {} );
  },
  //�����ʼ��
  _iniMenu: function() {
	this.hide();//���ز˵�
	this._buildMenu();//���ɲ˵�����
	this._forEachContainer(this._resetContainer);//������������
	this._insertMenu(0, 0);//��ʾ�˵�
  },
  //�����Զ���˵��������ɳ���˵�����
  _buildMenu: function() {
	//����ɲ˵�dom(�����Զ����)
	this._forEachMenu(function(o){
		var elem = o._elem;
		if ( elem ) {
			//��ֹdom�ڴ�й©
			$$E.removeEvent( elem, "mouseover", o._event );
			elem.parentNode.removeChild(elem);
			o._elem = o.elem = null;
		};
	});
	//���ò˵�Ĭ��ֵ
	var options = {
		id:				0,//id
		rank:			0,//����
		elem:			"",//�Զ���Ԫ��
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
	//�ȶ���"0"�����˵�
	this._menus = { "0": { "_children": [] } };
	//�����Զ���˵������뵽����˵�����
	$$A.forEach(this._custommenu, function(o) {
		//���ɲ˵�����(���ڰ�������Ҫ�������չ)
		var menu = $$.deepextend( $$.deepextend( {}, options ), o || {} );
		//ȥ����ͬid�˵���ͬʱ�ų���idΪ"0"�Ĳ˵�
		if ( !!this._menus[ menu.id ] ) { return; };
		//��������
		menu._children = []; menu._index = -1;
		this._menus[menu.id] = menu;
	}, this);
	//�������νṹ
	this._forEachMenu(function( o, id, menus ) {
		if ( "0" === id ) { return; };//����û�и����˵�
		var parent = this._menus[o.parent];
		//�����˵������ڻ��߸������Լ��Ļ�������һ���˵�
		if ( !parent || parent === o ) { parent = menus[o.parent = "0"]; };
		//���뵽�����˵������_children��
		parent._children.push(o);
	});
	//����˵�����
	this._forEachMenu(function(o) {
		//������Զ���Ԫ�صĻ��ȷŵ���Ƭ�ĵ���
		!!o.elem && ( o.elem = $$(o.elem) ) && this._frag.appendChild(o.elem);
		//������ʽ,����ʹ���Զ���Ԫ�ص�class
		if ( !!o.elem && o.elem.className ) {
			o.css = o.elem.className;
		} else if ( o.css === undefined ) { o.css = ""; };
		if ( o.hover === undefined ) { o.hover = o.css; };
		if ( o.active === undefined ) { o.active = o.hover; };
		//�Բ˵������_children��������(�Ȱ�rank�ٰ�id����)
		o._children.sort(function( x, y ) { return x.rank - y.rank || x.id - y.id; });
	});
  },
  //����˵�
  _insertMenu: function(index, parent) {
	var container = this._containers[index];
	//�����ͬһ�������˵������ظ�����
	if ( container._parent === parent ) { return; };
	container._parent = parent;
	//��ԭ�������ڲ˵��Ƶ���Ƭ������
	$$A.forEach( container._menus, function(o) { o._elem && this._frag.appendChild(o._elem); }, this );
	//�����Ӳ˵����󼯺�
	container._menus = [];
	//�ѴӸ����˵�Ԫ�ص��Ӳ˵����󼯺ϻ�ȡ��Ԫ�ز��뵽����
	$$A.forEach(this._menus[parent]._children, function( menu, i ){
		this._checkMenu( menu, index );//���˵�
		container._menus.push(menu);//���뵽�������Ӳ˵����ϣ��������
		container.menu.appendChild(menu._elem);//�˵�Ԫ�ز��뵽����
	}, this);
  },
  //���˵�
  _checkMenu: function(menu, index) {
	//�������浽�˵����������У��������
	menu._index = index;
	//����˵�����û��Ԫ��
	if ( !menu._elem ) {
		var elem = menu.elem;
		//���û���Զ���Ԫ�صĻ�����һ��
		if ( !elem ) { elem = document.createElement(menu.tag); elem.innerHTML = menu.html; };
		//����property
		$$.extend( elem, menu.property );
		//����attribute
		var attribute = menu.attribute;
		for (var att in attribute) { elem.setAttribute( att, attribute[att] ); };
		//������ʽ
		elem.className = menu.css;
		//�����¼�
		menu._event = $$F.bindAsEventListener( this._hoverMenu, this, menu );//��������¼�
		$$E.addEvent( elem, "mouseover", menu._event );
		//���浽�˵�����
		menu._elem = elem;
	};
  },
  //�����˵�
  _hoverMenu: function(e, menu) {
	var elem = menu._elem;
	//������ڲ�Ԫ�ش���ֱ�ӷ���
	if ( $$D.contains( elem, e.relatedTarget ) || elem === e.relatedTarget ) { return; };
	clearTimeout(this._timerMenu);
	//�����ڶ���������ƶ�������ȫ������������������ʽ
	this._forEachContainer(function(o, i){
		if ( o.pos.visibility === "hidden" ) { return; };
		this._resetCss(o);
		//���õ�ǰ�˵�Ϊactive��ʽ
		var menu = o._active;
		if ( menu ) { menu._elem.className = menu.active; };
	});
	//���õ�ǰ�˵�Ϊ������ʽ
	if ( this._containers[menu._index]._active !== menu ) { elem.className = menu.hover; };
	//������ʾ�˵�
	this._timerMenu = setTimeout( $$F.bind( this._showMenu, this, menu ), this.delay );
  },
  //��ʾ�˵�
  _showMenu: function(menu) {
	var index = menu._index, container = this._containers[index], child = !!menu._children.length;
	//���ز���Ҫ������
	this._forEachContainer( function(o, i) { i > index && this._hideContainer(o); } );
	//���õ�ǰ����_active
	container._active = null;
	//������Ӽ��˵�
	if ( child ) {
		//���õ�ǰ����_active
		container._active = menu;
		//��ʾ��һ������
		index++;//��������
		this._checkContainer(index);//�������
		this._insertMenu(index, menu.id);//����˵�
		this._showContainer(menu);//��ʾ����
	};
	//���õ�ǰ������css
	this._resetCss(container);
	//���õ�ǰ�˵���ʽ
	menu._elem.className = child ? menu.active : menu.hover;
  },
  //��ʼ������(����, ����Ԫ��)
  _iniContainer: function(index, container) {
	var oContainer = container.pos;
	//��������
	this._resetContainer(container);
	//����¼�
	$$E.addEvent(oContainer, "mouseover", $$F.bind(function(){ clearTimeout(this._timerContainer); }, this));
	$$E.addEvent(oContainer, "mouseout", $$F.bindAsEventListener(function(e){
		//���ж��Ƿ��Ƴ�����������֮��
		var elem = e.relatedTarget,
			isOut = $$A.every(this._containers, function(o){ return  o.pos == elem || !($$D.contains(o.pos, elem)); });
		if ( isOut ) {
			//�����ʱ��������
			clearTimeout(this._timerContainer); clearTimeout(this._timerMenu);
			this._timerContainer = setTimeout( $$F.bind( this.hide, this ), this.delay );
		};
	}, this));
	//���˵�һ�����������ø�����ʽ
	if ( index ) {
		$$D.setStyle(container.pos, {
			position: "absolute", display: "block", margin: 0,
			zIndex: this._containers[index - 1].pos.style.zIndex + 1//Ҫ����ĸ���ǰ���
		});
	};
	//ie6����select
	if ( $$B.ie6 ) {
		var iframe = document.createElement("<iframe style='position:absolute;filter:alpha(opacity=0);display:none;'>");
		document.body.insertBefore(iframe, document.body.childNodes[0]);
		container._iframe = iframe;
	};
	//��¼�������������
	container._index = index;
	//���뵽��������
	this._containers[index] = container;
  },
  //�������
  _checkContainer: function(index) {
	if ( index > 0 && !this._containers[index] ) {
		//������������ڣ�����ǰһ���������Ƴ�����������һ�����������Զ���
		var pre = this._containers[index - 1].pos
		//�����µ�����¼���ʽ��û��ie��cloneNode��bug
			,container = pre.parentNode.insertBefore( pre.cloneNode(false), pre );
		//���id��ֹ��ͻ
		container.id = "";
		//��ʼ������
		this._iniContainer( index, { "pos": container, "menu": container } );
	};
  },
  //��ʾ����
  _showContainer: function(menu) {
	var index = menu._index
		,container = this._containers[index + 1].pos
		,elem = menu.relContainer ? this._containers[index].pos : menu._elem
		,pos = RelativePosition( elem, container, menu.relative );
	//ִ����ʾǰ�¼�
	this.onBeforeShow(container, menu);
	//��λ����ʾ����
	$$D.setStyle(container, {
		left: pos.Left + "px", top: pos.Top + "px", visibility: "visible"
	});
	//ie6����select
	if ( $$B.ie6 ) {
		$$D.setStyle(this._containers[index + 1]._iframe, {
			width: container.offsetWidth + "px",
			height: container.offsetHeight + "px",
			left: pos.Left + "px", top: pos.Top + "px",
			display: ""
		});
	};
  },
  //��������
  _hideContainer: function(container) {
	//��������
	$$D.setStyle( container.pos, { left: "-9999px", top: "-9999px", visibility: "hidden" } );
	//������һ���˵��Ĵ����˵�����
	this._containers[container._index - 1]._active = null;
	//ie6����select
	if ( $$B.ie6 ) { container._iframe.style.display = "none"; };
  },
  //����������������
  _resetContainer: function(container) {
	container._active = null;//���ô����˵�
	container._menus = [];//�����Ӳ˵����󼯺�
	container._parent = -1;//���ø����˵�id
  },
  //���ز˵�
  hide: function() {
	this._forEachContainer(function(o, i){
		if ( i === 0 ) {
			//����ǵ�һ��������ʽ��_active
			this._resetCss(o);
		} else {//��������
			this._hideContainer(o);
		};
	});
  },
  //���������˵���ʽ
  _resetCss: function(container) {
	$$A.forEach( container._menus, function(o, i){ o._elem.className = o.css; }, this );
  },
  //����˵����󼯺�
  _forEachMenu: function(callback) {
	for ( var id in this._menus ) { callback.call( this, this._menus[id], id, this._menus ); };
  },
  //�����������󼯺�
  _forEachContainer: function(callback) {
	$$A.forEach( this._containers, callback, this );
  },
  //����Զ���˵�
  add: function(menu) {
	this._custommenu = this._custommenu.concat(menu);
	this._iniMenu();
  },
  //�޸��Զ���˵�
  edit: function(menu) {
	$$A.forEach( $$A.isArray( menu ) ? menu : [ menu ], function(o){
		//�����Ӧid�Ĳ˵�����
		if ( o.id && this._menus[o.id] ) {
			//���Զ���˵����ҳ���Ӧ�˵�,���޸�
			$$A.every( this._custommenu, function(m, i){
				if ( m.id === o.id ) {
					this._custommenu[i] = $$.deepextend( m, o ); return false;
				};
				return true;
				//��every��������ѭ��
			}, this );
		};
	}, this );
	this._iniMenu();
  },
  //ɾ���Զ���˵�
  del: function() {
	var ids = Array.prototype.slice.call(arguments);
	this._custommenu = $$A.filter( this._custommenu, function(o){
		return $$A.indexOf(ids, o.id) === -1;
	});
	this._iniMenu();
  }
};