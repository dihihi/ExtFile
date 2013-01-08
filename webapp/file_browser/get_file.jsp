<!DOCTYPE html>
<html>
<head>
    <title>ExtJS File Browser</title>
    <meta charset="utf-8" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" href="../scripts/ext4/resources/css/ext-all.css"/>
    <script type="text/javascript" src="../scripts/ext4/ext-all.js"></script>
    <script type="text/javascript" src="../scripts/ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="../scripts/ext4/ux/ckeditor.js"></script>
    
    <link rel="stylesheet" type="text/css" href="../scripts/syntaxhightlighter/styles/shCore.css" />
    <link rel="stylesheet" type="text/css" href="../scripts/syntaxhightlighter/styles/shThemeDefault.css" />
	<script language="javascript" type="text/javascript" src="../scripts/syntaxhightlighter/scripts/shCore.js"></script>
	<script language="javascript" type="text/javascript" src="../scripts/syntaxhightlighter/scripts/shBrushSql.js"></script>

    <style type="text/css">
    /*不考虑节点的状态，同一用一种图标显示*/
     .your-iconCls{background-image: url(../scripts/ext4/resources/themes/images/default/tree/folder.gif)}
    
    
    /*为节点的开闭状态和叶节点状态分别设置样式*/
     .your-iconCls{background-image: url(../scripts/ext4/resources/themes/images/default/tree/folder.gif)}
     .your-iconCls{background-image: url(../scripts/ext4/resources/themes/images/default/tree/folder.gif)}
      .your-iconCls{background-image: url(../scripts/ext4/resources/themes/images/default/tree/folder.gif)}
    </style>
    <script type="text/javascript">
    SyntaxHighlighter.all(); 
    
    Ext.onReady(function() {
       
        var fileType = null;//'file' or 'dir'
        var addFileWin = Ext.create('Ext.form.Panel', {
            title: 'AddFile',
            region: 'center',
            bodyPadding: 5,
            defaultType: 'textfield',
	      	items: [
	                  {
	                      id: 'file_name_input',
	                      name: 'name',
	                      labelWidth: 50,
	                      width: 300,
	                      lableAlign: 'right',
	                      fieldLabel: 'Name',
	                      allowBlank: false
	                  },{
	                      xtype : 'ckeditor',
	                      emptyText : '请输入页面参数！',
	                      allowBlank : true,
	                      fieldLabel : '参数',
	                      name : 'help',
	                      id : 'help',
	                      height : 300,
	                      width: 600,
	                      CKConfig : {
	                       toolbar : [{ name: 'document', items : [ 'NewPage','Preview' ] },
	                                  { name: 'clipboard', items : [ 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo' ] },
	                                  { name: 'editing', items : [ 'Find','Replace','-','SelectAll' ] },
	                                  { name: 'insert', items : [ 'Image','Flash','Table','HorizontalRule','SpecialChar','PageBreak','Iframe' ] },
	                                          '/',
	                                  { name: 'styles', items : [ 'Styles','Format','Font','FontSize' ] },
	                                  { name: 'colors', items : [ 'TextColor','BGColor' ] },
	                                  { name: 'basicstyles', items : [ 'Bold','Italic','Strike','-','RemoveFormat' ] },
	                                      '/',
	                                  { name: 'paragraph', items : [ 'NumberedList','BulletedList','-','Outdent','Indent','-','Outdent','Indent','-','Blockquote','-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock' ] },
	                                  { name: 'links', items : [ 'Link','Unlink','Anchor' ] },
	                                  { name: 'tools', items : [ 'Maximize', 'ShowBlocks','syntaxhighlight','Code', 'Source'] }],
	                       skin : 'office2003'
	                      }
	                     } ],
            listeners: { 
                'show': function() { 
                    Ext.getCmp('file_name_input').focus(true, true); 
                } 
            }
        });
        
       
        new Ext.Viewport({
                layout : "border",
                items : [addFileWin, {
                	id: 'asdf',
                	region: 'south',
                	html: 'asdf'
                }]
        });
      });
    </script>
</head>
<body >
<div id="asdf" height="300">
<pre class="brush:sql;">
(select              distinct             ru.mobile as mobile,             ru.user_name as name,      
      ru.user_id as user_id,             null as col5        
from kinghh_card_book cb, kinghh_user ru,kinghh_card c        
where cb.receiver_id = ru.user_id and cb.card_id=c.card_id        
and cb.receiver_id not in ( 34715, 23795, 34983, 34990, 35016 )        
and cb.sender_id in(34715, 23795, 34983, 34990, 35016)        
and  c.company_id=24 and (cb.is_delete = &#39;n&#39;)        
and (ru.mobile is null or ru.mobile!=&#39;88888888&#39;)        and           
(ru.user_name like concat(&#39;%&#39;,&#39;13844344150&#39;,&#39;%&#39;) or ru.mobile like concat(&#39;%&#39;,&#39;13844344150&#39;,&#39;%&#39;))                                 )        
union       
 (  select t.receiver_mobile as mobile,                  null as name,               
 t.sender_id as user_id,                 &#39;phoneMsg&#39; as col5          
from kinghh_to_not_user t          where sender_id in( 34715, 23795, 34983, 34990, 35016  )
and is_delete=&#39;n&#39; );
 
select * from kinghh_card_book where col1 is not null;
 
select * from kinghh_user order by user_id desc;
select * from kinghh_card where user_id=35016;</pre>

</div>

</body>
</html>

