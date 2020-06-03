import * as Winston from "winston";

import { ContainerModule, interfaces } from "inversify";
import { Logger, LoggerFactory, LoggerAssembly, processValue } from "acme-commons";

export const configuration = (): ContainerModule => {
    return new ContainerModule((bind) => {
        bind(LoggerAssembly.Level).toConstantValue(processValue("LOGGER_LEVEL", "info"));

        bind<interfaces.Factory<LoggerFactory>>(LoggerAssembly.Factory).toFactory((ctx) => (category?: string) => {
            const level = ctx.container.get<string>(LoggerAssembly.Level);
            const logger = Winston.createLogger({
                defaultMeta: { category },
                transports: [new Winston.transports.Console({ level })],
            });

            return logger;
        });

        bind<Logger>(LoggerAssembly.Instance)
            .toDynamicValue((ctx) => {
                return ctx.container.get<LoggerFactory>(LoggerAssembly.Factory)();
            })
            .whenTargetIsDefault();
    });
};
