<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	if (path.equals("/")) {
		path = "";
	}
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
	request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="${basePath}" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>首页</title>
<link rel="stylesheet" type="text/css" href="${basePath}/css/css.css" />
<script type="text/javascript"
	src="${basePath}/js/jquery/jquery-1.5.1.js"></script>
<script type="text/javascript"
	src="${basePath}/js/formValidator/formValidator.min.js" charset="UTF-8"></script>
<script type="text/javascript"
	src="${basePath}/js/formValidator/formValidatorRegex.js"
	charset="UTF-8"></script>
<script type="text/javascript" src="${basePath}/js/common.js"></script>
<script type="text/javascript" src="${basePath}/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
	var contextName = "${basePath}";
	function hideLeft() {
		$(".main").css("width", "1046px");
		$("ul.menu").hide();
	}
	function showLeft() {
		$(".main").css("width", "846px");
        $("ul.menu").show();
    }
	
	$(function() {
		CKEDITOR.replace('content');
		$.formValidator.initConfig({
			formID : "uploadForm",
			submitOnce : true,
			onSuccess : function() {
				return true;
			},
			onError : function(msg, obj, errorlist) {
				//$.map(errorlist,function(msg1){alert(msg1)});
				alert(msg);
			}
		});
		$("#question").formValidator({
			onShow : "",
			onFocus : "请输入问题描述",
			onCorrect : "　"
		}).inputValidator({
			min : 1,
			onError : "问题描述不能为空"
		}).inputValidator({
			max : 255,
			onError : "问题描述长度最多为255"
		});

		$("#orderNum").formValidator({
			onShow : "",
			onCorrect : "　",
			onFocus : "",
			empty : true
		}).regexValidator({
			regExp : "intege1",
			dataType : "enum",
			onError : "排序号只能输入数字"
		});

		$("#address").formValidator({
			onShow : "",
			onFocus : "请输入名片地址",
			onCorrect : "　"
		}).inputValidator({
			min : 1,
			onError : "名片地址不能为空"
		}).inputValidator({
			max : 256,
			onError : "名片地址长度最多为256"
		});

	});

	function myReset() {
		document.getElementById("uploadForm").reset();
		CKEDITOR.instances.answer
				.setData(document.getElementById("answer").defaultValue);
	}
</script>
</head>
<body>
	<%@include file="./heard.jsp" %>
	<div class="content">
	   <%@include file="./left.jsp" %>
		<div class="main">
			<div class="midmid">
				<!--主要内容区域-->
				<div class="iframe">
					<form id="articleForm" action="admin/gonggao_save.do" method="post">
    
				    <table width="700" border="0" cellspacing="0" cellpadding="0" style="margin-bottom:80px;">
					  <tr>
					    <td width="100" height="32" align="right" valign="middle">标题：</td>
					    <td><input type="text" name="title" value="" size="50" class="text">
					    	<input type="hidden" name="id" value=""/>
					    </td>
					  </tr>
					  <tr>
					   <td width="100" height="32" align="right" valign="middle">内容：</td>
					    <td><textarea class="ckeditor" name="profile" id="profile" cols="45" rows="5" style="width:580px;
					    height:80px;"></textarea></td>
					  </tr>
					  <tr>
					   <td width="100" height="32" align="right" valign="middle">下发范围：</td>
					    <td> &nbsp;&nbsp;
					    	<input type="checkbox" id="selectAll" />&nbsp;全部
					    	&nbsp;<input type="checkbox" class="ids" value="true" name="isYuantuanweiVisible"  />&nbsp;院团委
					    	&nbsp;<input type="checkbox" class="ids" value="true" name="isZongzhiVisible"/>&nbsp;团总支
					    	&nbsp;<input type="checkbox" class="ids" value="true" name="isZhibuVisible" />&nbsp;团支部
					    	&nbsp;<input type="checkbox" class="ids" value="true" name="isTuanyuanVisible" />&nbsp;团员
						</td>
					  </tr>
					  <tr>
					    <td height="60" align="right" valign="middle">&nbsp;</td>
					    <td style="padding-left:60px;"><input name="" type="submit" class="btn_1"  onmouseover= "this.className='btn_2' " onmouseout= "this.className='btn_1' " value="确 定" />
					      <input name="Input" type="button" class="btn_1"  onmouseover= "this.className='btn_2' " onmouseout= "this.className='btn_1' " onclick="location.href='admin/gonggao.do'" value="返 回" /></td>
					  </tr>
				    </table>
				    
			    </form>
				</div>
				<!--内容区域结束-->
			</div>
		</div>
	</div>
	<%@ include file="./footer.jsp" %>
</body>
</html>

