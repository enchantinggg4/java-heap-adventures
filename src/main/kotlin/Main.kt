import org.netbeans.lib.profiler.heap.Heap
import org.netbeans.lib.profiler.heap.HeapFactory
import org.netbeans.lib.profiler.heap.Instance
import org.netbeans.lib.profiler.heap.JavaClass
import java.io.File

private fun analyzeHeapDump() {
    val heap: Heap = HeapFactory.createHeap(File("D:\\IdeaProjects\\dragonme\\heap.hprof"))
    println(heap)
    val strClass: JavaClass = heap.getJavaClassByName("net.minecraft.item.Item")
    println(strClass)
    val instances: MutableList<Instance?> = strClass.instances as MutableList<Instance?>
    println(instances)
    for (instance in instances) {
        if(instance != null) {
            val nameFld: Any = instance.getValueOfField("maxCount")
//            val fieldValue: String = HeapUtils.processString(nameFld)
            System.out.println("Test:> $nameFld")
        }else {
            println("Instance is null $instance")
        }
    }
}

fun main(args: Array<String>) {
    analyzeHeapDump()
}