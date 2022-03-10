package ru.kingdomrpg.savior

import org.netbeans.lib.profiler.heap.*
import java.io.File


fun Heap.getClassFor(name: String): JavaClass {
    val classes = (allClasses as MutableList<JavaClass>)
    return classes.find { it.name == name }!!
}

private fun analyzeHeapDump(path: String) {
    val heap: Heap = HeapFactory.createHeap(File(path), 0)
    println("Loaded heap dump")

    val classes = (heap.allClasses as MutableList<JavaClass>)
    println("${heap.allClasses.size} classes")

    File("./import_data").mkdir()
    skillPaths(heap)
    skillLevels(heap)
    townChunks(heap)
    towns(heap)
    citizens(heap)
    kingdoms(heap)
    requests(heap)

}

inline fun <reified T : Any> writeToCSV(data: Collection<T>) {
    val fields = T::class.java.declaredFields
    val sb = StringBuilder()
    val header = fields.map { field ->
        field.name
    }.joinToString(",") + "\n"
    sb.append(header)
    data.forEach { item ->
        val row = fields.map {
            it.isAccessible = true

            val someValue = it.get(item)
            someValue?.toString() ?: "null"

        }.joinToString(",")
        sb.append(row).append("\n")
    }
    File("import_data/${T::class.java.simpleName}.csv").also {
        it.createNewFile()
    }.writeText(sb.toString())
}


fun skillPaths(heap: Heap) {
    val strClass: JavaClass = heap.getClassFor("ru.kingdomrp.krp.persistence.model.SkillPathModel")
    println("We got class, now will compute instances")
    val instances: MutableList<Instance> = strClass.instances as MutableList<Instance>
    println("Starting...")
    val skillPaths = instances.map { collectSkillPath(it) }
    println("Got ${skillPaths.size} skill paths")
    writeToCSV(skillPaths)
}

fun skillLevels(heap: Heap) {
    val strClass: JavaClass = heap.getClassFor("ru.kingdomrp.krp.persistence.model.SkillLevelModel")
    println("We got ${strClass}, now computing instances of SkillLevelModel")
    val instances: MutableList<Instance> = strClass.instances as MutableList<Instance>
    println("Starting...")
    val skillLevels = instances.map { collectSkillLevel(it) }
    println("Got ${skillLevels.size} skill levels")
    writeToCSV(skillLevels)
}

fun townChunks(heap: Heap) {
    val strClass: JavaClass = heap.getClassFor("ru.kingdomrp.krp.persistence.model.TownChunkModel")
    println("We got ${strClass}, now computing instances of TownChunkModel")
    val instances: MutableList<Instance> = strClass.instances as MutableList<Instance>
    println("Starting...")
    val chunks = instances.map { collectTownChunks(it) }
    println("Got ${chunks.size} town chunks")
    writeToCSV(chunks)
}

fun towns(heap: Heap) {
    val strClass: JavaClass = heap.getClassFor("ru.kingdomrp.krp.persistence.model.TownModel")
    println("We got ${strClass}, now computing instances of TownModel")
    val instances: MutableList<Instance> = strClass.instances as MutableList<Instance>
    println("Starting...")
    val towns = instances.map { collectTowns(it) }
    println("Got ${towns.size} ru.kingdomrpg.savior.towns")
    writeToCSV(towns)
}

fun citizens(heap: Heap) {
    val strClass: JavaClass = heap.getClassFor("ru.kingdomrp.krp.persistence.model.KingdomCitizenModel")
    println("We got ${strClass}, now computing instances of KingdomCitizenModel")
    val instances: MutableList<Instance> = strClass.instances as MutableList<Instance>
    println("Starting...")
    val citizens = instances.map { collectCitizens(it) }
    println("Got ${citizens.size} citizenss")
    writeToCSV(citizens)
}

fun kingdoms(heap: Heap) {
    val strClass: JavaClass = heap.getClassFor("ru.kingdomrp.krp.persistence.model.KingdomModel")
    println("We got ${strClass}, now computing instances of KingdomModel")
    val instances: MutableList<Instance> = strClass.instances as MutableList<Instance>
    println("Starting...")
    val kingdoms = instances.map { collectKingdoms(it) }
    println("Got ${kingdoms.size} ru.kingdomrpg.savior.kingdoms")
    writeToCSV(kingdoms)
}

fun requests(heap: Heap) {
    val strClass: JavaClass = heap.getClassFor("ru.kingdomrp.krp.persistence.model.KingdomRequestModel")
    println("We got $strClass , now computinginstances of KingdomRequestModel")
    val instances: MutableList<Instance> = strClass.instances as MutableList<Instance>
    println("Starting...")
    val requests = instances.map { collectRequests(it) }
    println("Got ${requests.size} kingdom ru.kingdomrpg.savior.requests")
    writeToCSV(requests)
}

fun main(args: Array<String>) {
    val path = args[0]
    println(path)
    analyzeHeapDump(path)
}