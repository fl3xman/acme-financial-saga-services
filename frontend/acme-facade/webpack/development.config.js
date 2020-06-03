const common = require("./common.config");

module.exports = () => {
    return {
        ...common(),

        mode: "development",
        devtool: "source-map",
        watch: true,
        devServer: {
            host: "0.0.0.0",
            port: 4000,
            historyApiFallback: true,
            publicPath: "/",
        }
    }
};