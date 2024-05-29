// 필요한 모듈과 CSS 파일을 가져옵니다.
const Editor = require('@toast-ui/editor');

require('@toast-ui/editor/dist/toastui-editor.css');
require('@toast-ui/editor/dist/toastui-editor-viewer.css');
require('@toast-ui/editor/dist/theme/toastui-editor-dark.css');
require('prismjs/themes/prism.css');
require('@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight.css');
require('tui-color-picker/dist/tui-color-picker.css');
require('@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css');
require('@toast-ui/editor/dist/i18n/ko-kr');


const CodeSyntaxHighlight = require('@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight-all.js');
const CodeSyntax = require('@toast-ui/editor-plugin-color-syntax');
const Prism = require('prismjs');
const Viewer = require('@toast-ui/editor/dist/toastui-editor-viewer');

// Editor 초기화
const editor = new Editor({
  el: document.querySelector('#editor'),
  height: '850px',
  initialEditType: 'markdown',
  previewStyle: 'tab',
  theme: 'dark',
  plugins: [CodeSyntax, CodeSyntaxHighlight],
  initialValue: document.getElementById("editor-body").value, // 초기 값 설정
  language: 'ko-KR',
});
editor.getMarkdown();
