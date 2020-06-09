const common = require("./common.config");

module.exports = () => {
    return {
        ...common(),

        mode: "development",
        devtool: "source-map",
        watch: true,
        devServer: {
            host: "0.0.0.0",
            port: 4001,
            historyApiFallback: true,
            publicPath: "/",
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Access-Control-Allow-Methods": "GET, POST, PUT, DELETE, PATCH, OPTIONS",
                "Access-Control-Allow-Headers": "X-Requested-With, content-type, Authorization"
            }
        }
    }
};