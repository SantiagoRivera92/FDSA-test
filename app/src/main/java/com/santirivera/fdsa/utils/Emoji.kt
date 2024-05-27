package com.santirivera.fdsa.utils

fun String.toFlagEmoji(): String {

        /**
         * There are 26 regional indicator symbols from A to Z.
         * These are represented in hex from 0x1F1E6 to 0x1F1FF.
         * If you combine any two of these characters to form a valid ISO-3166 country code
         * then it will show a flag emoji for that country/entity.
         *
         * The regional indicators go from 0x1F1E6 (A) to 0x1F1FF (Z).
         *
         * To calculate the equivalent regional indicator for a character,
         * we can add 0x1F1E6 to its ascii value (the value for regional indicator A),
         * then subtract 0x41 (the value for uppercase A). 0x1F1E6 - 0x41 = 0x1F1A5.
         *
         * It's sad but I think I've spent more time in this class than in the rest of the project
         * but at least I haven't had to download a bunch of flags to represent the countries
         * and make the biggest when statement ever.
         **/

        val countryCodeOffset = 0x1F1A5;

        /**
         * These only work for uppercase characters, so we'll make sure they're uppercase first
         */

        val upperCase = this.uppercase()

        val firstChar = Character.codePointAt(upperCase, 0) + countryCodeOffset
        val secondChar = Character.codePointAt(upperCase, 1) + countryCodeOffset
        return String(Character.toChars(firstChar)) + String(Character.toChars(secondChar))
}