package ru.kingdomrpg.savior

import org.netbeans.lib.profiler.heap.*
import java.util.*


fun Instance.i(name: String): Instance {
    return this.getValueOfField(name) as Instance
}

inline fun <reified T> Instance.f(name: String): T {
    return this.getValueOfField(name) as T
}

fun Instance.uuid(name: String): UUID {
    val s = this.i(name)
    val leastSigBits = s.f<Long>("leastSigBits")
    val mostSigBits = s.f<Long>("mostSigBits")
    return UUID(mostSigBits, leastSigBits)
}

fun Instance.string(name: String): String {

    val stringObj = this.i(name)
    val ba = stringObj.f<PrimitiveArrayInstance>("value")
    val values = ba.values
    val sb = StringBuilder()
    println("Bebr")
    println(ba.toString())

    values.forEach {
        val s = (it as String)
        print("S: $s")
        val b = s.toByte()
        print(", B: $b")
    }

//    values.forEach {
//
//        val wta = if (it is Char) {
//            it
//        } else if (it is String) {
//            val charCode = Integer.valueOf(it)
//            Character.toString(charCode.toChar())
//        }else "_"
//
//        println("[$wta] [$it]")
//        sb.append(wta)
//
//    }
//    println("VALUES: $values")
//    println("nigger? its me $ba")
//    println("Good: <${sb}>")
    return "help"
}