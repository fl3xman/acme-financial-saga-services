const HtmlWebpackPlugin = require("html-webpack-plugin");
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
        },

        plugins: [
            ...common().plugins,

            new HtmlWebpackPlugin({
                title: "ACME Financial Payment Services",
                template: "templates/root.html",
                filename: "index.html",
                assets: "assets",
                inject: true,
            })
        ]
    }
};