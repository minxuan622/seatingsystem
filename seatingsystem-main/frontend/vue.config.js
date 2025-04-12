module.exports = {
  // 打包輸出路徑 - 開發完成後使用
  //outputDir: '../demo/src/main/resources/static',

  // 開發時的代理設置
  devServer: {
    port: 8801,
    proxy: {
      '/api': {
        target: 'http://localhost:8080', // Spring Boot 的地址
        changeOrigin: true,
        pathRewrite: { '^/api': '/api' }
      }
    }
  },

  // 確保資源引用的路徑是相對路徑
  publicPath: './'
}