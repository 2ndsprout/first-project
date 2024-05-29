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

document.addEventListener('DOMContentLoaded', function() {
  const editor = new Editor({
    el: document.querySelector('#editor'),
    height: '800px',
    initialEditType: 'markdown',
    previewStyle: 'tab',
    theme: 'dark',
    plugins: [CodeSyntax, CodeSyntaxHighlight],
    initialValue: document.getElementById("editor-body").value,
    language: 'ko-KR',
  });

  window.updateForm = function() {
    const form = document.getElementById("updateForm");
    const editorBody = document.getElementById("editor-body");
    editorBody.value = editor.getMarkdown();
    if (confirm("수정하시겠습니까?")) {
      form.submit();
    }
  };
});
