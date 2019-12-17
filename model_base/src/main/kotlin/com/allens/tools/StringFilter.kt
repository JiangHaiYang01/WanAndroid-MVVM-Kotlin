package com.allens.tools

/**

 * @Author allens
 * @Date 2019-12-17-17:01
 * @Email 18856907654@163.com
 */

//过滤出数字
fun filterNumber(number: String): String {
    return number.replace("[^(0-9)]".toRegex(), "")
}

//过滤出字母
fun filterAlphabet(alph: String): String {
    return alph.replace("[^(A-Za-z)]".toRegex(), "")
}

//过滤出中文
fun filterChinese(chin: String): String {
    return chin.replace("[^(\\u4e00-\\u9fa5)]".toRegex(), "")
}

//过滤出字母、数字和中文
fun filter(character: String): String {
    return character.replace("[^(a-zA-Z0-9\\u4e00-\\u9fa5)]".toRegex(), "")
}
