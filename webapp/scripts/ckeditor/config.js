/*
Copyright (c) 2003-2012, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig = function( config )
{
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	
	//firefox image paste,when such an image is pasted it's saved as a new file at the server and that url is used instead of the huge data
	config.extraPlugins = 'syntaxhighlight,imagepaste';
};
