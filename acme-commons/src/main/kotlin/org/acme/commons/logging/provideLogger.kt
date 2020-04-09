package org.acme.commons.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

inline fun <reified T> T.provideLogger(): Logger =
    if (T::class.isCompanion) LoggerFactory.getLogger(T::class.java.enclosingClass)
    else LoggerFactory.getLogger(T::class.java)