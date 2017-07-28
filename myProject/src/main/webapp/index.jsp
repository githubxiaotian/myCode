<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="overflow:hidden;">
<head>
<%@ include file="/WEB-INF/page/commons/meta.jsp"%>
<link href="${ctx}/WEB-INF/theme/${styleName}/css/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/WEB-INF/theme/${styleName}/css/main.css" rel="stylesheet" type="text/css" />
<!-- 新css示例页 by- yugh   2017-06-02 -->
<link href="${ctx }/WEB-INF/theme/${styleName}/css/gh-buttons.css" rel="stylesheet" type="text/css" />
 <!-- add yugh -->
<link href="${ctx }/WEB-INF/widget/dialog/style_new.css" rel="stylesheet" />
<script src="${ctx }/WEB-INF/widget/dialog/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/WEB-INF/widget/linkedSelect/linkedSelect.js" type="text/javascript"></script>
<script src="${ctx}/WEB-INF/widget/jquery-validate/jquery.validate.js" type="text/javascript"></script>
<script src="${ctx}/WEB-INF/widget/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/WEB-INF/widget/dialog/jquery_dialog.js"></script>
<script type="text/javascript">
$(function() {
	var result = "${msg}";
	if( result != null && result != "" ){
		if(result == '1'){
			Ext.MessageBox.alert("提示","自采产品信息添加成功！");
		}else if(result == '2'){
			Ext.MessageBox.alert("错误","自采产品信息添加失败,请重试！");
		}else if(result == '3'){
			Ext.MessageBox.alert("提示","自采产品信息修改成功！");
		}else if(result == '4'){
			Ext.MessageBox.alert("错误","自采产品信息修改失败,请重试！");
		}else if(result == '5'){
			Ext.MessageBox.alert("错误","自采产品信息删除完成！");
		}else if(result == '6'){
			Ext.MessageBox.alert("错误","自采产品信息删除失败，请重试！");
		}else if(result == '7'){
			Ext.MessageBox.alert("提示","自采批量数据导入成功！");
		}else if(result == '8'){
			Ext.MessageBox.alert("提示","自采批量数据导入失败，请检查文档内容是否正确,(物料编号、类型、出资方、采购方式)不能为空！");
		}else if(result == '9'){
			Ext.MessageBox.alert("提示","自采批量数据导入失败，文档没有sheet页信息！");
		}
	}
});

//关闭窗口
function closeOpen(){
	$("#excel").val("");
	resetBtn();
	$("#checkDownload").val("0");
	//Ext.MessageBox.alert("取消","已取消上传excel文件！");
}

//查询
function doSearch() {
 	$("#myform").attr("action","${ctx}/yugh/testPage");
	$("#myform").submit();
	$("#_loadingDiv").show();
}
//重置
function resetBtn(){
	 var inps = document.getElementsByTagName("input");
	 	for (var i = 0; i < inps.length; i++) {
	 		if(inps[i].type == "text" || inps[i].type == "hidden"){
	 			inps[i].value = "" ;
	 		}
	 	}
	 	$("#purchCHannel").val("");
	 	$("#status").val("");
	 	$("#dataSource").val("");
}


strArr = new Array();
//全部勾选 
$(function() {
	$('#checkboxAll').click(function() {
		if ($('#checkboxAll').attr('checked') == true) {
			$('#checkList #checkBox_name').attr('checked', true);
			for ( var i = 0; i < $('#checkList #checkBox_name').length; i++) {
				strArr[i] = $('#checkList #checkBox_name')[i].value;
			}
		} else {
			$('#checkList #checkBox_name').attr('checked', false);
			var count = strArr.length;
			for ( var k = 0; k < count; k++) {//删除数组元素
				if (strArr.length != 0) {
					strArr.shift();
				}
			}
		}
	});
});

//产品详情窗口
function showProdDetails(id){
  	openExtWin2('${ctx}/platform/selfPurchaseProduct/prodSelfPurchaseDetails.do?prodId=' + id+'&operation=1', 1000, 460, '自采产品详细信息');
}

//新增
function addProd(){
	window.location.href = '${ctx}/platform/selfPurchaseProduct/prodSelfPurchaseDetails.do?operation=2';
}
//删除
function delProd(){
	var sum = "";
	var checkBox = document.getElementsByName("checkBox_name");
	for(var i=0;i<checkBox.length;i++){
		if(checkBox[i].checked){
			var prodId = $("#checkId_"+checkBox[i].value).val();
			if(prodId != null){
				sum += prodId +",";
			}
		}
	}
	if(sum != null && sum != ''){
		Ext.MessageBox.confirm('删除', '是否确认删除，删除后数据消失？', function callBack(id) {
			if(id == "yes") {
				 $("#myform").attr("action", "${ctx}/platform/selfPurchaseProduct/deleteSelfProduct.do?ids="+sum.substring(0, sum.length-1));
				 $('#myform').submit();
				 $("#_loadingDiv").show();
			}
		});
	}else {
		Ext.MessageBox.alert("提示","请选择一条数据后操作！");
	}
}

//导入excel规范文档，批量插入数据库，失败返回提示并回滚 - 暂时备用- yugh
function importExcel(){
	Ext.MessageBox.confirm('导入', '导入前请确认excel文档内数据符合规范,是‘继续’,否‘no’ !', function callBack(id) {
		if(id == "yes") {
			//
		}
	});
}
//上传excel
function uploadFeils(){
	var down = $("#checkDownload").val();
	if(down == '0'){
		alert("请下载模板样式，将严格按照格式要求，下载附件时请勿关闭上传小窗 !");
		return false;
	}  
	var excel = $("#excel").val();
	var index = excel.lastIndexOf(".");
	if(index == -1){
		alert("请上传自采产品excel文件 !");
	  	return false;
	}
 	var type  = excel.substring(index+1);
    if(type != "xlsx" ){
    	alert("附件格式为xlsx后缀，xls后缀请修改为xlsx !");
  	   	return false;
  	}
    var imageSize = document.getElementById("excel").files[0].size;
    if(imageSize < 100000){
    	alert("数据内容较少，请手工添加 !");
	  	return false;
    }
	$("#currenUrl").val(window.location.href);
	var conf = confirm("请确认附件模板内数据格式按照规范上传？");
	if(conf == true){
		$("#fileBtn").attr("action","${ctx}/platform/selfPurchaseProduct/importSelfExcel.do");
		$("#fileBtn").submit();
		$('.theme-popover').hide();
		$('#one').removeAttr('href');//去掉a标签中的href属性
		$('#one').removeAttr('onclick');//去掉a标签中的onclick事件
		$('#more').removeAttr('href');
		$('#more').removeAttr('onclick');
		$('#uploadExcel').removeAttr('href');
		$('#uploadExcel').removeAttr('onclick');
		//上传期间禁止所有操作，且zindex最高 - by- yugh
		$("#uploadExcel").hide();
		$("#seache").attr("disabled",true);
		$("#restBtn").attr("disabled",true);
		$("#_loadingDiv").show();
	}else{
		//给提示
	}
}
//判断是否下载附件
function checkDown(){
	$("#checkDownload").val("1");
}
</script>
</head>
<body>
<div class="h_6"></div>
<div class="box_m">
	<div class="tit">
		<div class="tit_bg">
	<form id="myform" action="${ctx}/yugh/testPage" method="post">
		<table cellpadding="0" cellspacing="0" border="0" class="tab_case_01"  >
			 <tr>
			    <td colspan="1" style="text-align: right;">
			      <input type="button" id="seache" value="测试跳转" onclick="doSearch();" class="button icon edit" />
			    </td>
			 </tr>
			</table>
		</div>
	</form>
	</div>
	</div>
</body>
</html>