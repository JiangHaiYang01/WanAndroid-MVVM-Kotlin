package com.allens.tools

/**

 * @Author allens
 * @Date 2019-12-17-20:09
 * @Email 18856907654@163.com
 */

//颜色加上 透明度
fun getColorWithAlpha(alpha: Float, baseColor: Int): Int {
    val a = 255.coerceAtMost(0.coerceAtLeast((alpha * 255).toInt())) shl 24
    val rgb = 0x00ffffff and baseColor
    return a + rgb
}