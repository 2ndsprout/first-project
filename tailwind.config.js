module.exports = {
  content: [
    './src/main/resources/**/*.{html,js}', // 프로젝트에 맞게 경로를 수정하세요.
  ],
  theme: {
    extend: {},
  },
  plugins: [
    require('daisyui'),
  ],
  daisyui: {
    themes: ["light", "dark", "cupcake"], // 원하는 테마를 추가하세요.
  },
}