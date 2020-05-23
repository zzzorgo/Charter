package zzz.zzzorgo.charter.utils

import dagger.Component
import javax.inject.Inject

// @Component makes Dagger create a graph of dependencies
@Component
interface ApplicationGraph {
    fun test(): TestDagger
}


class TestDagger @Inject constructor() {
    fun test() {
        println("1")
    }
}