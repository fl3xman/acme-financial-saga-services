const HtmlWebpackPlugin = require("html-webpack-plugin");
const Webpack = require("webpack");
const { resolve } = require("path");

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
            {
                test: /\.tsx?$/,
                use: [
                    {
                        loader: 'ts-loader',
                        options: {
                            allowTsInNodeModules: true
                        }
                    }
                ]
            },
            {
                test: /\.yaml$/,
                use: "js-yaml-loader"
            }
        ],
    };
    base.optimization = {
        runtimeChunk: {
            name: "vendor"
        },
        splitChunks: {
            cacheGroups: {
                default: false,
                commons: {
                    test: /node_modules/,
                    name: "vendor",
                    chunks: "initial",
                    minSize: 1
                }
            }
        }
    };
    base.resolve = {
        extensions: [".ts", ".tsx", ".js", ".json"],
    };
    base.plugins = [
        new HtmlWebpackPlugin({
            title: "ACME Financial Services",
            template: "templates/root.html",
            filename: "index.html",
            assets: "assets",
            inject: true,
        }),
        new Webpack.DefinePlugin({
            "process.env": {
                "LOGGER_LEVEL": JSON.stringify("debug"),
            }
        }),
    ];

    return base;
};