package algorithm_4.section_2

import edu.princeton.cs.introcs.StdDraw

/**
 * 归并排序——自底向上排序
 *
 *  自底向上的归并排序会多次遍历整个数组，根据子数组的大小进行两两并归。子数组 sz 的初始值为1 ，每次加倍。最后一个子数组的大小只有
 *  排序数组是偶数倍的时候才会等于 sz 否者他会比 sz 小。所以当排序数组的大小为 2^N 的时候，自底向上和自定向下的访问次数和交换次数
 *  是完全一致的。可以通过棒状图很清晰的看出来
 *
 * {@link Merge} 自顶向下排序
 */
class MergeBU {


    companion object {

        /**
         * java 版本
         * for(sz=1;sz<n;sz=sz+sz){        1-n sz = 1 2 4 8 16 32 64
         *    for(lo=0;lo<n;lo+=sz+sz){    0-n lo = 0 2 4 6 8 10
         *       merge(....)               2-n lo = 0 4 8 12 16
         *      }                          .....
         * }
         */
        fun sort(array: Array<Double>) {
            array.show()
            val n = array.size
            val aux: Array<Double?> = arrayOfNulls(array.size)
            //写个双重 for 循环真受罪
            //sz:子数组大小
            //lo:子数组索引
            var sz = 1
            while (sz < n) {
                var lo = 0
                while (lo < n - sz) {
                    merge(array, aux, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, n - 1))
                    lo += sz + sz
                }
                sz += sz
            }
            array.show(StdDraw.GREEN)
        }

        private fun merge(a: Array<Double>, aux: Array<Double?>, lo: Int, mid: Int, hi: Int) {
            var i = lo
            var j = mid + 1
            var changedIndex: Int
            for (k in lo..hi) {
                aux[k] = a[k]
            }
            for (k in lo..hi) {
                a[k] = when {
                //左半边用尽
                    i > mid -> {
                        changedIndex = j
                        aux[j++]!!
                    }
                //右半边用尽
                    j > hi -> {
                        changedIndex = i
                        aux[i++]!!
                    }
                //右半边的当前元素小于左半边的元素，取右半边的元素
                    less(aux[j]!!, aux[i]!!) -> {
                        changedIndex = j
                        aux[j++]!!
                    }
                //右半边的当前元素大于左半边元素，取左半边元素
                    else -> {
                        changedIndex = i
                        aux[i++]!!
                    }
                }
                a.betterShow(changedIndex, k)
            }
        }

        @JvmStatic
        fun main(str: Array<String>) {
//            SortCompare.match("Merge", "MergeBU", 50, 2)
            MergeBU.sort(SortCompare.uniformArray())
        }

    }
}