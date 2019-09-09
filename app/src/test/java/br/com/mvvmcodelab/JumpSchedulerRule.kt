package br.com.mvvmcodelab

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import io.reactivex.functions.Function


class JumpSchedulerRule : TestRule {

    private val jumpScheduler = Function<Scheduler, Scheduler> { Schedulers.trampoline() }

    override fun apply(statement: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxJavaPlugins.setIoSchedulerHandler(jumpScheduler)
                RxJavaPlugins.setComputationSchedulerHandler(jumpScheduler)
                RxJavaPlugins.setNewThreadSchedulerHandler(jumpScheduler)
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

                try {
                    statement.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}