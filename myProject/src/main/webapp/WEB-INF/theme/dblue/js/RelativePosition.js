var RelativePosition = function(){
	function getLeft( align, rect, rel ){
		var iLeft = 0;
		switch (align.toLowerCase()) {
			case "left" :
				return rect.left - rel.offsetWidth;
			case "clientleft" :
				return rect.left;
			case "center" :
				return ( rect.left + rect.right - rel.offsetWidth ) / 2;
			case "clientright" :
				return rect.right - rel.offsetWidth;
			case "right" :
			default :
				return rect.right;
		};
	};
	function getTop( valign, rect, rel ){
		var iTop = 0;
		switch (valign.toLowerCase()) {
			case "top" :
				return rect.top - rel.offsetHeight;
			case "clienttop" :
				return rect.top;
			case "center" :
				return ( rect.top + rect.bottom - rel.offsetHeight ) / 2;
			case "clientbottom" :
				return rect.bottom - rel.offsetHeight;
			case "bottom" :
			default :
				return rect.bottom;
		};
	};
	//��λԪ�� ��Զ�λԪ��
	return function ( fix, rel, options ) {
		//Ĭ��ֵ
		var opt = $$.extend({
			align:			"clientleft",//ˮƽ����λ
			vAlign:			"clienttop",//��ֱ����λ
			customLeft:		0,//�Զ���left��λ
			customTop:		0,//�Զ���top��λ
			percentLeft:	0,//�Զ���left�ٷֱȶ�λ
			percentTop:		0,//�Զ���top�ٷֱȶ�λ
			adaptive:		false,//�Ƿ�����Ӧ��λ
			reset:			false//����Ӧ��λʱ�Ƿ����¶�λ
		}, options || {});
		//�������
		var rect = $$D.clientRect(fix)
			,iLeft = getLeft(opt.align, rect, rel) + opt.customLeft
			,iTop = getTop(opt.vAlign, rect, rel) + opt.customTop;
		//�Զ���ٷֱȶ�λ
		if (opt.percentLeft) { iLeft += .01 * opt.percentLeft * fix.offsetWidth; };
		if (opt.percentTop) { iTop += .01 * opt.percentTop * fix.offsetHeight; };
		//����Ӧ�Ӵ���λ
		if (opt.adaptive) {
			//������λ����
			var doc = fix.ownerDocument
				,maxLeft = doc.clientWidth - rel.offsetWidth
				,maxTop = doc.clientHeight - rel.offsetHeight;
			if (opt.reset) {
				//�Զ����¶�λ
				if (iLeft > maxLeft || iLeft < 0) {
					iLeft = getLeft(2 * iLeft > maxLeft ? "left" : "right", rect, rel) + opt.customLeft;
				};
				if (iTop > maxTop || iTop < 0) {
					iTop = getTop(2 * iTop > maxTop ? "top" : "bottom", rect, rel) + opt.customTop;
				};
			} else {
				//�������ʺ�λ��
				iLeft = Math.max(Math.min(iLeft, maxLeft), 0);
				iTop = Math.max(Math.min(iTop, maxTop), 0);
			};
		};
		//���Ϲ�����
		iLeft += $$D.getScrollLeft(fix); iTop += $$D.getScrollTop(fix);
		//���ض�λ����
		return { Left: iLeft, Top: iTop };
	};
}();