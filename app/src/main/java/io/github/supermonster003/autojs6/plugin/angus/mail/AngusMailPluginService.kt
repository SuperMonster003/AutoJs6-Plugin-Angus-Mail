package io.github.supermonster003.autojs6.plugin.angus.mail

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

/**
 * Entry point the AutoJs6 host binds to (action `org.autojs.plugin.MAIL`, category `mail`).
 *
 * Until the host `mail-api` contract is staged in `libs/` (roadmap P1.1 / P1.3), the service hands
 * out a placeholder Binder that carries only the `IMailPlugin` descriptor, so discovery, binding,
 * and the descriptor check already work end to end. P2.5 replaces it with the `IMailPlugin.Stub`
 * that routes requests to the mail core.
 */
class AngusMailPluginService : Service() {

    private val binder: IBinder = Binder().apply {
        attachInterface(null, AngusMailPlugin.SERVICE_DESCRIPTOR)
    }

    override fun onBind(intent: Intent?): IBinder = binder
}
