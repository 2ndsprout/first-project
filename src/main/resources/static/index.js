const Editor = require('@toast-ui/editor');

require('@toast-ui/editor/dist/toastui-editor.css');
require('@toast-ui/editor/dist/toastui-editor-viewer.css');
require('@toast-ui/editor/dist/theme/toastui-editor-dark.css');
require('prismjs/themes/prism.css');
require('@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight.css');

const codeSyntaxHighlight = require('@toast-ui/editor-plugin-code-syntax-highlight');



const editor = new Editor({
  el: document.querySelector('#editor'),
  height: '600px',
  width: '500px',
  initialEditType: 'markdown',
  previewStyle: 'vertical',
  theme: 'dark',
  plugins: [codeSyntaxHighlight]
});