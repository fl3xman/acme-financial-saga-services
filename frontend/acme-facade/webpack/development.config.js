const Webpack = require("webpack");
const common = require("./common.config");

module.exports = () => {
    return {
        ...common(),

        mode: "development",
        devtool: "source-map",
        watch: true,
        devServer: {
            host: "0.0.0.0",
            port: 3000,
            historyApiFallback: true,
            publicPath: "/",
        },

        plugins: [
            ...common().plugins,

            new Webpack.DefinePlugin({
                "process.env": {
                    "LOGGER_LEVEL": "debug"
                }
            }),
        ]
    }
};