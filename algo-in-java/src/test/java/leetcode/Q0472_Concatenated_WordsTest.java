package leetcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Q0472_Concatenated_WordsTest {
    private String[] words1;
    private String[] words2;
    private String[] words3;
    private String[] words4;

    @BeforeEach
    void setup() {
        words1 = "cat,bat,rat,catrat,batcat,batcatrat,cats".split(",");
        words2 = "cat,bat,catbat".split(",");
        words3 = new String[]{"cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"};
        // 42 / 43 testcases passed. Time Limit Exceeded on #43 test case, i.e. below words
        words4 = new String[]{"a", "aa", "aaa", "aaaa", "aaaaa", "aaaaaa", "aaaaaaa", "aaaaaaaa",
                "aaaaaaaaa", "aaaaaaaaaa", "aaaaaaaaaaa", "aaaaaaaaaaaa", "aaaaaaaaaaaaa", "aaaaaaaaaaaaaa",
                "aaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaa",
                "aaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaa",
                "aaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaa",
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaz",
                "b", "bb", "bbb", "bbbb", "bbbbb", "bbbbbb", "bbbbbbb", "bbbbbbbb", "bbbbbbbbb", "bbbbbbbbbb", "bbbbbbbbbbb", "bbbbbbbbbbbb",
                "bbbbbbbbbbbbb", "bbbbbbbbbbbbbb", "bbbbbbbbbbbbbbb", "bbbbbbbbbbbbbbbb", "bbbbbbbbbbbbbbbbb", "bbbbbbbbbbbbbbbbbb", "bbbbbbbbbbbbbbbbbbb",
                "bbbbbbbbbbbbbbbbbbbb", "bbbbbbbbbbbbbbbbbbbbb", "bbbbbbbbbbbbbbbbbbbbbb", "bbbbbbbbbbbbbbbbbbbbbbb", "bbbbbbbbbbbbbbbbbbbbbbbb",
                "bbbbbbbbbbbbbbbbbbbbbbbbb", "bbbbbbbbbbbbbbbbbbbbbbbbbb", "bbbbbbbbbbbbbbbbbbbbbbbbbbb", "bbbbbbbbbbbbbbbbbbbbbbbbbbbb", "bbbbbbbbbbbbbbbbbbbbbbbbbbbbb",
                "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbb", "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbz",
                "c", "cc", "ccc", "cccc", "ccccc", "cccccc", "ccccccc", "cccccccc", "ccccccccc", "cccccccccc", "ccccccccccc", "cccccccccccc", "ccccccccccccc", "cccccccccccccc", "ccccccccccccccc", "cccccccccccccccc", "ccccccccccccccccc", "cccccccccccccccccc", "ccccccccccccccccccc", "cccccccccccccccccccc", "ccccccccccccccccccccc", "cccccccccccccccccccccc", "ccccccccccccccccccccccc", "cccccccccccccccccccccccc", "ccccccccccccccccccccccccc", "cccccccccccccccccccccccccc", "ccccccccccccccccccccccccccc", "cccccccccccccccccccccccccccc", "ccccccccccccccccccccccccccccc", "cccccccccccccccccccccccccccccc", "cccccccccccccccccccccccccccccz", "d", "dd", "ddd", "dddd", "ddddd", "dddddd", "ddddddd", "dddddddd", "ddddddddd", "dddddddddd", "ddddddddddd", "dddddddddddd", "ddddddddddddd", "dddddddddddddd", "ddddddddddddddd", "dddddddddddddddd", "ddddddddddddddddd", "dddddddddddddddddd", "ddddddddddddddddddd", "dddddddddddddddddddd", "ddddddddddddddddddddd", "dddddddddddddddddddddd", "ddddddddddddddddddddddd", "dddddddddddddddddddddddd", "ddddddddddddddddddddddddd", "dddddddddddddddddddddddddd", "ddddddddddddddddddddddddddd", "dddddddddddddddddddddddddddd", "ddddddddddddddddddddddddddddd", "dddddddddddddddddddddddddddddd", "dddddddddddddddddddddddddddddz", "e", "ee", "eee", "eeee", "eeeee", "eeeeee", "eeeeeee", "eeeeeeee", "eeeeeeeee", "eeeeeeeeee", "eeeeeeeeeee", "eeeeeeeeeeee", "eeeeeeeeeeeee", "eeeeeeeeeeeeee", "eeeeeeeeeeeeeee", "eeeeeeeeeeeeeeee", "eeeeeeeeeeeeeeeee", "eeeeeeeeeeeeeeeeee", "eeeeeeeeeeeeeeeeeee", "eeeeeeeeeeeeeeeeeeee", "eeeeeeeeeeeeeeeeeeeee", "eeeeeeeeeeeeeeeeeeeeee", "eeeeeeeeeeeeeeeeeeeeeee", "eeeeeeeeeeeeeeeeeeeeeeee", "eeeeeeeeeeeeeeeeeeeeeeeee", "eeeeeeeeeeeeeeeeeeeeeeeeee", "eeeeeeeeeeeeeeeeeeeeeeeeeee", "eeeeeeeeeeeeeeeeeeeeeeeeeeee", "eeeeeeeeeeeeeeeeeeeeeeeeeeeee", "eeeeeeeeeeeeeeeeeeeeeeeeeeeeee", "eeeeeeeeeeeeeeeeeeeeeeeeeeeeez", "f", "ff", "fff", "ffff", "fffff", "ffffff", "fffffff", "ffffffff", "fffffffff", "ffffffffff", "fffffffffff", "ffffffffffff", "fffffffffffff", "ffffffffffffff", "fffffffffffffff", "ffffffffffffffff", "fffffffffffffffff", "ffffffffffffffffff", "fffffffffffffffffff", "ffffffffffffffffffff", "fffffffffffffffffffff", "ffffffffffffffffffffff", "fffffffffffffffffffffff", "ffffffffffffffffffffffff", "fffffffffffffffffffffffff", "ffffffffffffffffffffffffff", "fffffffffffffffffffffffffff", "ffffffffffffffffffffffffffff", "fffffffffffffffffffffffffffff", "ffffffffffffffffffffffffffffff", "fffffffffffffffffffffffffffffz", "g", "gg", "ggg", "gggg", "ggggg", "gggggg", "ggggggg", "gggggggg", "ggggggggg", "gggggggggg", "ggggggggggg", "gggggggggggg", "ggggggggggggg", "gggggggggggggg", "ggggggggggggggg", "gggggggggggggggg", "ggggggggggggggggg", "gggggggggggggggggg", "ggggggggggggggggggg", "gggggggggggggggggggg", "ggggggggggggggggggggg", "gggggggggggggggggggggg", "ggggggggggggggggggggggg", "gggggggggggggggggggggggg", "ggggggggggggggggggggggggg", "gggggggggggggggggggggggggg", "ggggggggggggggggggggggggggg", "gggggggggggggggggggggggggggg", "ggggggggggggggggggggggggggggg", "gggggggggggggggggggggggggggggg", "gggggggggggggggggggggggggggggz", "h", "hh", "hhh", "hhhh", "hhhhh", "hhhhhh", "hhhhhhh", "hhhhhhhh", "hhhhhhhhh", "hhhhhhhhhh", "hhhhhhhhhhh", "hhhhhhhhhhhh", "hhhhhhhhhhhhh", "hhhhhhhhhhhhhh", "hhhhhhhhhhhhhhh", "hhhhhhhhhhhhhhhh", "hhhhhhhhhhhhhhhhh", "hhhhhhhhhhhhhhhhhh", "hhhhhhhhhhhhhhhhhhh", "hhhhhhhhhhhhhhhhhhhh", "hhhhhhhhhhhhhhhhhhhhh", "hhhhhhhhhhhhhhhhhhhhhh", "hhhhhhhhhhhhhhhhhhhhhhh", "hhhhhhhhhhhhhhhhhhhhhhhh", "hhhhhhhhhhhhhhhhhhhhhhhhh", "hhhhhhhhhhhhhhhhhhhhhhhhhh", "hhhhhhhhhhhhhhhhhhhhhhhhhhh", "hhhhhhhhhhhhhhhhhhhhhhhhhhhh", "hhhhhhhhhhhhhhhhhhhhhhhhhhhhh", "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhh", "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhz", "i", "ii", "iii", "iiii", "iiiii", "iiiiii", "iiiiiii", "iiiiiiii", "iiiiiiiii", "iiiiiiiiii", "iiiiiiiiiii", "iiiiiiiiiiii", "iiiiiiiiiiiii", "iiiiiiiiiiiiii", "iiiiiiiiiiiiiii", "iiiiiiiiiiiiiiii", "iiiiiiiiiiiiiiiii", "iiiiiiiiiiiiiiiiii", "iiiiiiiiiiiiiiiiiii", "iiiiiiiiiiiiiiiiiiii", "iiiiiiiiiiiiiiiiiiiii", "iiiiiiiiiiiiiiiiiiiiii", "iiiiiiiiiiiiiiiiiiiiiii", "iiiiiiiiiiiiiiiiiiiiiiii", "iiiiiiiiiiiiiiiiiiiiiiiii", "iiiiiiiiiiiiiiiiiiiiiiiiii", "iiiiiiiiiiiiiiiiiiiiiiiiiii", "iiiiiiiiiiiiiiiiiiiiiiiiiiii", "iiiiiiiiiiiiiiiiiiiiiiiiiiiii", "iiiiiiiiiiiiiiiiiiiiiiiiiiiiii", "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiz", "j", "jj", "jjj", "jjjj", "jjjjj", "jjjjjj", "jjjjjjj", "jjjjjjjj", "jjjjjjjjj", "jjjjjjjjjj", "jjjjjjjjjjj", "jjjjjjjjjjjj", "jjjjjjjjjjjjj", "jjjjjjjjjjjjjj", "jjjjjjjjjjjjjjj", "jjjjjjjjjjjjjjjj", "jjjjjjjjjjjjjjjjj", "jjjjjjjjjjjjjjjjjj", "jjjjjjjjjjjjjjjjjjj", "jjjjjjjjjjjjjjjjjjjj", "jjjjjjjjjjjjjjjjjjjjj", "jjjjjjjjjjjjjjjjjjjjjj", "jjjjjjjjjjjjjjjjjjjjjjj", "jjjjjjjjjjjjjjjjjjjjjjjj", "jjjjjjjjjjjjjjjjjjjjjjjjj", "jjjjjjjjjjjjjjjjjjjjjjjjjj", "jjjjjjjjjjjjjjjjjjjjjjjjjjj", "jjjjjjjjjjjjjjjjjjjjjjjjjjjj", "jjjjjjjjjjjjjjjjjjjjjjjjjjjjj", "jjjjjjjjjjjjjjjjjjjjjjjjjjjjjj", "jjjjjjjjjjjjjjjjjjjjjjjjjjjjjz", "k", "kk", "kkk", "kkkk", "kkkkk", "kkkkkk", "kkkkkkk", "kkkkkkkk", "kkkkkkkkk", "kkkkkkkkkk", "kkkkkkkkkkk", "kkkkkkkkkkkk", "kkkkkkkkkkkkk", "kkkkkkkkkkkkkk", "kkkkkkkkkkkkkkk", "kkkkkkkkkkkkkkkk", "kkkkkkkkkkkkkkkkk", "kkkkkkkkkkkkkkkkkk", "kkkkkkkkkkkkkkkkkkk", "kkkkkkkkkkkkkkkkkkkk", "kkkkkkkkkkkkkkkkkkkkk", "kkkkkkkkkkkkkkkkkkkkkk", "kkkkkkkkkkkkkkkkkkkkkkk", "kkkkkkkkkkkkkkkkkkkkkkkk", "kkkkkkkkkkkkkkkkkkkkkkkkk", "kkkkkkkkkkkkkkkkkkkkkkkkkk", "kkkkkkkkkkkkkkkkkkkkkkkkkkk", "kkkkkkkkkkkkkkkkkkkkkkkkkkkk", "kkkkkkkkkkkkkkkkkkkkkkkkkkkkk", "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkk", "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkz", "l", "ll", "lll", "llll", "lllll", "llllll", "lllllll", "llllllll", "lllllllll", "llllllllll", "lllllllllll", "llllllllllll", "lllllllllllll", "llllllllllllll", "lllllllllllllll", "llllllllllllllll", "lllllllllllllllll", "llllllllllllllllll", "lllllllllllllllllll", "llllllllllllllllllll", "lllllllllllllllllllll", "llllllllllllllllllllll", "lllllllllllllllllllllll", "llllllllllllllllllllllll", "lllllllllllllllllllllllll", "llllllllllllllllllllllllll", "lllllllllllllllllllllllllll", "llllllllllllllllllllllllllll", "lllllllllllllllllllllllllllll", "llllllllllllllllllllllllllllll", "lllllllllllllllllllllllllllllz", "m", "mm", "mmm", "mmmm", "mmmmm", "mmmmmm", "mmmmmmm", "mmmmmmmm", "mmmmmmmmm", "mmmmmmmmmm", "mmmmmmmmmmm", "mmmmmmmmmmmm", "mmmmmmmmmmmmm", "mmmmmmmmmmmmmm", "mmmmmmmmmmmmmmm", "mmmmmmmmmmmmmmmm", "mmmmmmmmmmmmmmmmm", "mmmmmmmmmmmmmmmmmm", "mmmmmmmmmmmmmmmmmmm", "mmmmmmmmmmmmmmmmmmmm", "mmmmmmmmmmmmmmmmmmmmm", "mmmmmmmmmmmmmmmmmmmmmm", "mmmmmmmmmmmmmmmmmmmmmmm", "mmmmmmmmmmmmmmmmmmmmmmmm", "mmmmmmmmmmmmmmmmmmmmmmmmm", "mmmmmmmmmmmmmmmmmmmmmmmmmm", "mmmmmmmmmmmmmmmmmmmmmmmmmmm", "mmmmmmmmmmmmmmmmmmmmmmmmmmmm", "mmmmmmmmmmmmmmmmmmmmmmmmmmmmm", "mmmmmmmmmmmmmmmmmmmmmmmmmmmmmm", "mmmmmmmmmmmmmmmmmmmmmmmmmmmmmz", "n", "nn", "nnn", "nnnn", "nnnnn", "nnnnnn", "nnnnnnn", "nnnnnnnn", "nnnnnnnnn", "nnnnnnnnnn", "nnnnnnnnnnn", "nnnnnnnnnnnn", "nnnnnnnnnnnnn", "nnnnnnnnnnnnnn", "nnnnnnnnnnnnnnn", "nnnnnnnnnnnnnnnn", "nnnnnnnnnnnnnnnnn", "nnnnnnnnnnnnnnnnnn", "nnnnnnnnnnnnnnnnnnn", "nnnnnnnnnnnnnnnnnnnn", "nnnnnnnnnnnnnnnnnnnnn", "nnnnnnnnnnnnnnnnnnnnnn", "nnnnnnnnnnnnnnnnnnnnnnn", "nnnnnnnnnnnnnnnnnnnnnnnn", "nnnnnnnnnnnnnnnnnnnnnnnnn", "nnnnnnnnnnnnnnnnnnnnnnnnnn", "nnnnnnnnnnnnnnnnnnnnnnnnnnn", "nnnnnnnnnnnnnnnnnnnnnnnnnnnn", "nnnnnnnnnnnnnnnnnnnnnnnnnnnnn", "nnnnnnnnnnnnnnnnnnnnnnnnnnnnnn", "nnnnnnnnnnnnnnnnnnnnnnnnnnnnnz", "o", "oo", "ooo", "oooo", "ooooo", "oooooo", "ooooooo", "oooooooo", "ooooooooo", "oooooooooo", "ooooooooooo", "oooooooooooo", "ooooooooooooo", "oooooooooooooo", "ooooooooooooooo", "oooooooooooooooo", "ooooooooooooooooo", "oooooooooooooooooo", "ooooooooooooooooooo", "oooooooooooooooooooo", "ooooooooooooooooooooo", "oooooooooooooooooooooo", "ooooooooooooooooooooooo", "oooooooooooooooooooooooo", "ooooooooooooooooooooooooo", "oooooooooooooooooooooooooo", "ooooooooooooooooooooooooooo", "oooooooooooooooooooooooooooo", "ooooooooooooooooooooooooooooo", "oooooooooooooooooooooooooooooo", "oooooooooooooooooooooooooooooz", "p", "pp", "ppp", "pppp", "ppppp", "pppppp", "ppppppp", "pppppppp", "ppppppppp", "pppppppppp", "ppppppppppp", "pppppppppppp", "ppppppppppppp", "pppppppppppppp", "ppppppppppppppp", "pppppppppppppppp", "ppppppppppppppppp", "pppppppppppppppppp", "ppppppppppppppppppp", "pppppppppppppppppppp", "ppppppppppppppppppppp", "pppppppppppppppppppppp", "ppppppppppppppppppppppp", "pppppppppppppppppppppppp", "ppppppppppppppppppppppppp", "pppppppppppppppppppppppppp", "ppppppppppppppppppppppppppp", "pppppppppppppppppppppppppppp", "ppppppppppppppppppppppppppppp", "pppppppppppppppppppppppppppppp", "pppppppppppppppppppppppppppppz", "q", "qq", "qqq", "qqqq", "qqqqq", "qqqqqq", "qqqqqqq", "qqqqqqqq", "qqqqqqqqq", "qqqqqqqqqq", "qqqqqqqqqqq", "qqqqqqqqqqqq", "qqqqqqqqqqqqq", "qqqqqqqqqqqqqq", "qqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqz", "r", "rr", "rrr", "rrrr", "rrrrr", "rrrrrr", "rrrrrrr", "rrrrrrrr", "rrrrrrrrr", "rrrrrrrrrr", "rrrrrrrrrrr", "rrrrrrrrrrrr", "rrrrrrrrrrrrr", "rrrrrrrrrrrrrr", "rrrrrrrrrrrrrrr", "rrrrrrrrrrrrrrrr", "rrrrrrrrrrrrrrrrr", "rrrrrrrrrrrrrrrrrr", "rrrrrrrrrrrrrrrrrrr", "rrrrrrrrrrrrrrrrrrrr", "rrrrrrrrrrrrrrrrrrrrr", "rrrrrrrrrrrrrrrrrrrrrr", "rrrrrrrrrrrrrrrrrrrrrrr", "rrrrrrrrrrrrrrrrrrrrrrrr", "rrrrrrrrrrrrrrrrrrrrrrrrr", "rrrrrrrrrrrrrrrrrrrrrrrrrr", "rrrrrrrrrrrrrrrrrrrrrrrrrrr", "rrrrrrrrrrrrrrrrrrrrrrrrrrrr", "rrrrrrrrrrrrrrrrrrrrrrrrrrrrr", "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrr", "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrz", "s", "ss", "sss", "ssss", "sssss", "ssssss", "sssssss", "ssssssss", "sssssssss", "ssssssssss", "sssssssssss", "ssssssssssss", "sssssssssssss", "ssssssssssssss", "sssssssssssssss", "ssssssssssssssss", "sssssssssssssssss", "ssssssssssssssssss", "sssssssssssssssssss", "ssssssssssssssssssss", "sssssssssssssssssssss", "ssssssssssssssssssssss", "sssssssssssssssssssssss", "ssssssssssssssssssssssss", "sssssssssssssssssssssssss", "ssssssssssssssssssssssssss", "sssssssssssssssssssssssssss", "ssssssssssssssssssssssssssss", "sssssssssssssssssssssssssssss", "ssssssssssssssssssssssssssssss", "sssssssssssssssssssssssssssssz", "t", "tt", "ttt", "tttt", "ttttt", "tttttt", "ttttttt", "tttttttt", "ttttttttt", "tttttttttt", "ttttttttttt", "tttttttttttt", "ttttttttttttt", "tttttttttttttt", "ttttttttttttttt", "tttttttttttttttt", "ttttttttttttttttt", "tttttttttttttttttt", "ttttttttttttttttttt", "tttttttttttttttttttt", "ttttttttttttttttttttt", "tttttttttttttttttttttt", "ttttttttttttttttttttttt", "tttttttttttttttttttttttt", "ttttttttttttttttttttttttt", "tttttttttttttttttttttttttt", "ttttttttttttttttttttttttttt", "tttttttttttttttttttttttttttt", "ttttttttttttttttttttttttttttt", "tttttttttttttttttttttttttttttt", "tttttttttttttttttttttttttttttz", "u", "uu", "uuu", "uuuu", "uuuuu", "uuuuuu", "uuuuuuu", "uuuuuuuu", "uuuuuuuuu", "uuuuuuuuuu", "uuuuuuuuuuu", "uuuuuuuuuuuu", "uuuuuuuuuuuuu", "uuuuuuuuuuuuuu", "uuuuuuuuuuuuuuu", "uuuuuuuuuuuuuuuu", "uuuuuuuuuuuuuuuuu", "uuuuuuuuuuuuuuuuuu", "uuuuuuuuuuuuuuuuuuu", "uuuuuuuuuuuuuuuuuuuu", "uuuuuuuuuuuuuuuuuuuuu", "uuuuuuuuuuuuuuuuuuuuuu", "uuuuuuuuuuuuuuuuuuuuuuu", "uuuuuuuuuuuuuuuuuuuuuuuu", "uuuuuuuuuuuuuuuuuuuuuuuuu", "uuuuuuuuuuuuuuuuuuuuuuuuuu", "uuuuuuuuuuuuuuuuuuuuuuuuuuu", "uuuuuuuuuuuuuuuuuuuuuuuuuuuu", "uuuuuuuuuuuuuuuuuuuuuuuuuuuuu", "uuuuuuuuuuuuuuuuuuuuuuuuuuuuuu", "uuuuuuuuuuuuuuuuuuuuuuuuuuuuuz", "v", "vv", "vvv", "vvvv", "vvvvv", "vvvvvv", "vvvvvvv", "vvvvvvvv", "vvvvvvvvv", "vvvvvvvvvv", "vvvvvvvvvvv", "vvvvvvvvvvvv", "vvvvvvvvvvvvv", "vvvvvvvvvvvvvv", "vvvvvvvvvvvvvvv", "vvvvvvvvvvvvvvvv", "vvvvvvvvvvvvvvvvv", "vvvvvvvvvvvvvvvvvv", "vvvvvvvvvvvvvvvvvvv", "vvvvvvvvvvvvvvvvvvvv", "vvvvvvvvvvvvvvvvvvvvv", "vvvvvvvvvvvvvvvvvvvvvv", "vvvvvvvvvvvvvvvvvvvvvvv", "vvvvvvvvvvvvvvvvvvvvvvvv", "vvvvvvvvvvvvvvvvvvvvvvvvv", "vvvvvvvvvvvvvvvvvvvvvvvvvv", "vvvvvvvvvvvvvvvvvvvvvvvvvvv", "vvvvvvvvvvvvvvvvvvvvvvvvvvvv", "vvvvvvvvvvvvvvvvvvvvvvvvvvvvv", "vvvvvvvvvvvvvvvvvvvvvvvvvvvvvv", "vvvvvvvvvvvvvvvvvvvvvvvvvvvvvz", "w", "ww", "www", "wwww", "wwwww", "wwwwww", "wwwwwww", "wwwwwwww", "wwwwwwwww", "wwwwwwwwww", "wwwwwwwwwww", "wwwwwwwwwwww", "wwwwwwwwwwwww", "wwwwwwwwwwwwww", "wwwwwwwwwwwwwww", "wwwwwwwwwwwwwwww", "wwwwwwwwwwwwwwwww", "wwwwwwwwwwwwwwwwww", "wwwwwwwwwwwwwwwwwww", "wwwwwwwwwwwwwwwwwwww", "wwwwwwwwwwwwwwwwwwwww", "wwwwwwwwwwwwwwwwwwwwww", "wwwwwwwwwwwwwwwwwwwwwww", "wwwwwwwwwwwwwwwwwwwwwwww", "wwwwwwwwwwwwwwwwwwwwwwwww", "wwwwwwwwwwwwwwwwwwwwwwwwww", "wwwwwwwwwwwwwwwwwwwwwwwwwww", "wwwwwwwwwwwwwwwwwwwwwwwwwwww", "wwwwwwwwwwwwwwwwwwwwwwwwwwwww", "wwwwwwwwwwwwwwwwwwwwwwwwwwwwww", "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwz", "x", "xx", "xxx", "xxxx", "xxxxx", "xxxxxx", "xxxxxxx", "xxxxxxxx", "xxxxxxxxx", "xxxxxxxxxx", "xxxxxxxxxxx", "xxxxxxxxxxxx", "xxxxxxxxxxxxx", "xxxxxxxxxxxxxx", "xxxxxxxxxxxxxxx", "xxxxxxxxxxxxxxxx", "xxxxxxxxxxxxxxxxx", "xxxxxxxxxxxxxxxxxx", "xxxxxxxxxxxxxxxxxxx", "xxxxxxxxxxxxxxxxxxxx", "xxxxxxxxxxxxxxxxxxxxx", "xxxxxxxxxxxxxxxxxxxxxx", "xxxxxxxxxxxxxxxxxxxxxxx", "xxxxxxxxxxxxxxxxxxxxxxxx", "xxxxxxxxxxxxxxxxxxxxxxxxx", "xxxxxxxxxxxxxxxxxxxxxxxxxx", "xxxxxxxxxxxxxxxxxxxxxxxxxxx", "xxxxxxxxxxxxxxxxxxxxxxxxxxxx", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxx", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxz", "y", "yy", "yyy", "yyyy", "yyyyy", "yyyyyy", "yyyyyyy", "yyyyyyyy", "yyyyyyyyy", "yyyyyyyyyy", "yyyyyyyyyyy", "yyyyyyyyyyyy", "yyyyyyyyyyyyy", "yyyyyyyyyyyyyy", "yyyyyyyyyyyyyyy", "yyyyyyyyyyyyyyyy", "yyyyyyyyyyyyyyyyy", "yyyyyyyyyyyyyyyyyy", "yyyyyyyyyyyyyyyyyyy", "yyyyyyyyyyyyyyyyyyyy", "yyyyyyyyyyyyyyyyyyyyy", "yyyyyyyyyyyyyyyyyyyyyy", "yyyyyyyyyyyyyyyyyyyyyyy", "yyyyyyyyyyyyyyyyyyyyyyyy", "yyyyyyyyyyyyyyyyyyyyyyyyy", "yyyyyyyyyyyyyyyyyyyyyyyyyy", "yyyyyyyyyyyyyyyyyyyyyyyyyyy", "yyyyyyyyyyyyyyyyyyyyyyyyyyyy", "yyyyyyyyyyyyyyyyyyyyyyyyyyyyy", "yyyyyyyyyyyyyyyyyyyyyyyyyyyyyy", "yyyyyyyyyyyyyyyyyyyyyyyyyyyyyz"};
    }

    @Test
    void findAllConcatenatedWordsInADict() {
        Q0472_Concatenated_Words sol = new Q0472_Concatenated_Words();
        sol.runAllVersions(Arrays.toString(words1) + " expect: 3", () -> sol.findAllConcatenatedWordsInADict(words1), null);
        sol.runAllVersions(Arrays.toString(words2) + " expect: 1", () -> sol.findAllConcatenatedWordsInADict(words2), null);
        sol.runAllVersions(Arrays.toString(words3) + " expect: 3", () -> sol.findAllConcatenatedWordsInADict(words3), null);
    }

    @Disabled
    @Test
    void testLargeInput() {
        Q0472_Concatenated_Words sol = new Q0472_Concatenated_Words();
        long start = System.currentTimeMillis();

        sol.performMeasure("test leetcode #43 time exceeded limit  case", () -> {
            List<String> results = sol.findAllConcatenatedWordsInADict(words4);
            assertEquals(725, results.size());
            return null;
        }, 1);
    }
}