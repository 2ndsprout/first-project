const Editor = require('@toast-ui/editor');

require('@toast-ui/editor/dist/toastui-editor.css');
require('@toast-ui/editor/dist/toastui-editor-viewer.css');
require('@toast-ui/editor/dist/theme/toastui-editor-dark.css');
require('prismjs/themes/prism.css');
require('@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight.css');
require('tui-color-picker/dist/tui-color-picker.css');
require('@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css');

const codeSyntaxHighlight = require('@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight-all.js');
const codeSyntax = require('@toast-ui/editor-plugin-color-syntax');
const Prism = require('prismjs');

const editor = new Editor({
  el: document.querySelector('#editor'),
  height: '800px',
  initialEditType: 'wysiwyg',
  previewStyle: 'vertical',
  theme: 'dark',
  plugins: [codeSyntax, codeSyntaxHighlight]
});