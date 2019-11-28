module.exports = {
  "transpileDependencies": [
    "vuetify"
  ],
  devServer: {
    proxy: {
      '^/api' : {
        target: 'http://localhost:9000',
        ws: true,
        changeOrigin: true
      },
    },
  },
};