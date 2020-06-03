import { LFService, LogLevel, LoggerFactoryOptions, LogGroupRule } from "typescript-logging";

import { ContainerModule, interfaces } from "inversify";
import { Logger, LoggerFactory, LoggerAssembly, processValue } from "acme-commons";

export const configuration = (): ContainerModule => {
    return new ContainerModule((bind) => {
        bind(LoggerAssembly.Level).toConstantValue(processValue("LOGGER_LEVEL", LogLevel.Debug));

        bind<interfaces.Factory<LoggerFactory>>(LoggerAssembly.Factory).toFactory((ctx) => (category?: string) => {
            const level = ctx.container.get<number>(LoggerAssembly.Level);
            const options = new LoggerFactoryOptions();
            options.addLogGroupRule(new LogGroupRule(new RegExp(""), level));

            const factory = LFService.createLoggerFactory(options);
            const logger = factory.getLogger(category || "");

            return logger;
        });

        bind<Logger>(LoggerAssembly.Instance)
            .toDynamicValue((ctx) => {
                return ctx.container.get<LoggerFactory>(LoggerAssembly.Factory)();
            })
            .whenTargetIsDefault();
    });
};
