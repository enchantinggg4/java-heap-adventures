import org.netbeans.lib.profiler.heap.Instance
import org.netbeans.lib.profiler.heap.ObjectArrayInstance
import org.netbeans.lib.profiler.heap.PrimitiveArrayInstance

class HeapUtils {
    /**
     * Processes a LinkedList
     * @param instance - LinkedList instance
     */
    fun processLinkedList(instance: Instance) {
        var listEntry = instance.getValueOfField("first") as Instance
        var i = 0
        while (listEntry != null) {
            i++
            val item = listEntry.getValueOfField("item")
            println("Type: $item")
            // process the item in the list
            listEntry = listEntry.getValueOfField("next") as Instance
        }
    }

    /**
     * Processes an ArrayList
     * @param instance - ArrayList instance
     * @return instance list
     */
    fun processArrayList(instance: Instance): MutableList<Any?>? {
        val data = instance.getValueOfField("elementData") as ObjectArrayInstance
        return data.values
    }

    companion object {
        fun processString(instance: Instance): String {
            if (instance.getValueOfField("value") is PrimitiveArrayInstance) {
                val pi = instance.getValueOfField("value") as PrimitiveArrayInstance
                if (pi != null) {
                    val entries = pi.values
                    val builder = StringBuilder()
                    for (obj in entries) {
                        if (obj is Char) {
                            builder.append(obj)
                        } else if (obj is Int) {
                            val charCode = Integer.valueOf(obj as String?)
                            builder.append(Character.toString(charCode.toChar()))
                        }
                    }
                    return builder.toString()
                }
            } else {
                return instance.getValueOfField("value").toString()
            }
            return "null"
        }
    }
}