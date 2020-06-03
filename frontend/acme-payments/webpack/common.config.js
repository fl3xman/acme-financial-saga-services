const Webpack = require("webpack");
const { join, resolve } = require("path");

module.exports = () => {
    const base = {};

    base.entry = "./src/index.tsx";
    base.output = {
        path: resolve(__dirname, "../dist"),
        publicPath: "/",
        filename: "[name].[hash].js",
    };
    base.node = { console: false, fs: "empty", net: "empty", tls: "empty" };
    base.module = {
        rules: [
            { test: /\.tsx?$/, loader: "ts-loader", options: { allowTsInNodeModules: true } },
            { test: /\.yaml$/, use: "js-yaml-loader" }
        ],
    };
    base.optimization = {};
    base.resolve = {
        extensions: [".ts", ".tsx", ".js", ".json"],
    };
    base.plugins = [
        new Webpack.DefinePlugin({
            "process.env": {
                "LOGGER_LEVEL": "debug"
            }
        })
    ];

    return base;
};