package test.assertk.assertions

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import test.assertk.opentestPackageName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class IterableTest {
    //region contains
    @Test fun contains_element_present_passes() {
        assertThat(listOf(1, 2) as Iterable<Int>).contains(2)
    }

    @Test fun contains_element_missing_fails() {
        val error = assertFails {
            assertThat(emptyList<Any?>() as Iterable<Any?>).contains(1)
        }
        assertEquals("expected to contain:<1> but was:<[]>", error.message)
    }
    //endregion

    //region doesNotContain
    @Test fun doesNotContain_element_missing_passes() {
        assertThat(emptyList<Any?>() as Iterable<Any?>).doesNotContain(1)
    }

    @Test fun doesNotContain_element_present_fails() {
        val error = assertFails {
            assertThat(listOf(1, 2) as Iterable<Int>).doesNotContain(2)
        }
        assertEquals("expected to not contain:<2> but was:<[1, 2]>", error.message)
    }
    //endregion

    //region containsNone
    @Test fun containsNone_missing_elements_passes() {
        assertThat(emptyList<Any?>() as Iterable<Any?>).containsNone(1)
    }

    @Test fun containsNone_present_element_fails() {
        val error = assertFails {
            assertThat(listOf(1, 2) as Iterable<Int>).containsNone(2, 3)
        }
        assertEquals(
            """expected to contain none of:<[2, 3]> but was:<[1, 2]>
                | elements not expected:<[2]>
            """.trimMargin(), error.message
        )
    }
    //region

    //region containsAll
    @Test fun containsAll_all_elements_passes() {
        assertThat(listOf(1, 2) as Iterable<Int>).containsAll(2, 1)
    }

    @Test fun containsAll_some_elements_fails() {
        val error = assertFails {
            assertThat(listOf(1) as Iterable<Int>).containsAll(1, 2)
        }
        assertEquals(
            """expected to contain all:<[1, 2]> but was:<[1]>
                | elements not found:<[2]>
            """.trimMargin(), error.message
        )
    }
    //endregion

    //region containsOnly
    @Test fun containsOnly_only_elements_passes() {
        assertThat(listOf(1, 2) as Iterable<Int>).containsOnly(2, 1)
    }

    @Test fun containsOnly_duplicate_elements_passes() {
        assertThat(listOf(1, 2, 2) as Iterable<Int>).containsOnly(2, 1)
    }

    @Test fun containsOnly_duplicate_elements_passes2() {
        assertThat(listOf(1, 2) as Iterable<Int>).containsOnly(2, 2, 1)
    }

    @Test fun containsOnly_more_elements_fails() {
        val error = assertFails {
            assertThat(listOf(1, 2, 3) as Iterable<Int>).containsOnly(2, 1)
        }
        assertEquals(
            """expected to contain only:<[2, 1]> but was:<[1, 2, 3]>
                | extra elements found:<[3]>
            """.trimMargin(), error.message
        )
    }

    @Test fun containsOnly_less_elements_fails() {
        val error = assertFails {
            assertThat(listOf(1, 2, 3) as Iterable<Int>).containsOnly(2, 1, 3, 4)
        }
        assertEquals(
            """expected to contain only:<[2, 1, 3, 4]> but was:<[1, 2, 3]>
                | elements not found:<[4]>
            """.trimMargin(),
            error.message
        )
    }

    @Test fun containsOnly_different_elements_fails() {
        val error = assertFails {
            assertThat(listOf(1) as Iterable<Int>).containsOnly(2)
        }
        assertEquals(
            """expected to contain only:<[2]> but was:<[1]>
                | elements not found:<[2]>
                | extra elements found:<[1]>
            """.trimMargin(),
            error.message
        )
    }
    //endregion

    //region containsExactlyInAnyOrder
    @Test fun containsExactlyInAnyOrder_only_elements_passes() {
        assertThat(listOf(1, 2) as Iterable<Int>).containsExactlyInAnyOrder(2, 1)
    }

    @Test fun containsExactlyInAnyOrder_only_elements_passes2() {
        assertThat(listOf(1, 2, 1) as Iterable<Int>).containsExactlyInAnyOrder(2, 1, 1)
    }

    @Test fun containsExactlyInAnyOrder_duplicate_elements_fails() {
        val error = assertFails {
            assertThat(listOf(1, 2, 2) as Iterable<Int>).containsExactlyInAnyOrder(2, 1)
        }
        assertEquals(
            """expected to contain exactly in any order:<[2, 1]> but was:<[1, 2, 2]>
                | extra elements found:<[2]>
            """.trimMargin(), error.message
        )
    }

    @Test fun containsExactlyInAnyOrder_duplicate_elements_fails2() {
        val error = assertFails {
            assertThat(listOf(1, 2) as Iterable<Int>).containsExactlyInAnyOrder(2, 2, 1)
        }
        assertEquals(
            """expected to contain exactly in any order:<[2, 2, 1]> but was:<[1, 2]>
                | elements not found:<[2]>
            """.trimMargin(), error.message
        )
    }

    @Test fun containsExactlyInAnyOrder_more_elements_fails() {
        val error = assertFails {
            assertThat(listOf(1, 2, 3) as Iterable<Int>).containsExactlyInAnyOrder(2, 1)
        }
        assertEquals(
            """expected to contain exactly in any order:<[2, 1]> but was:<[1, 2, 3]>
                | extra elements found:<[3]>
            """.trimMargin(), error.message
        )
    }

    @Test fun containsExactlyInAnyOrder_less_elements_fails() {
        val error = assertFails {
            assertThat(listOf(1, 2, 3) as Iterable<Int>).containsExactlyInAnyOrder(2, 1, 3, 4)
        }
        assertEquals(
            """expected to contain exactly in any order:<[2, 1, 3, 4]> but was:<[1, 2, 3]>
                | elements not found:<[4]>
            """.trimMargin(),
            error.message
        )
    }

    @Test fun containsExactlyInAnyOrder_different_elements_fails() {
        val error = assertFails {
            assertThat(listOf(1) as Iterable<Int>).containsExactlyInAnyOrder(2)
        }
        assertEquals(
            """expected to contain exactly in any order:<[2]> but was:<[1]>
                | elements not found:<[2]>
                | extra elements found:<[1]>
            """.trimMargin(),
            error.message
        )
    }
    //endregion

    //region containsSublist
    @Test fun containsSublist_equal_lists_passes() {
        assertThat(listOf(1, 2, 3) as Iterable<Int>).containsSublist(1, 2, 3)
    }

    @Test fun containsSublist_extra_items_in_front_passes() {
        assertThat(listOf(2, 1, 2, 3) as Iterable<Int>).containsSublist(2, 3)
    }

    @Test fun containsSublist_extra_items_behind_passes() {
        assertThat(listOf(1, 2, 3) as Iterable<Int>).containsSublist(1, 2)
    }

    @Test fun containsSublist_missing_element_fails() {
        val error = assertFails {
            assertThat(listOf(1, 2, 3) as Iterable<Int>).containsSublist(4, 5)
        }
        assertEquals(
            """expected to contain sublist:<[4, 5]> but was:<[1, 2, 3]>
              | first missing element:<4>
            """.trimMargin().lines(), error.message!!.lines()
        )
    }

    @Test fun containsSublist_extra_element_fails() {
        val error = assertFails {
            assertThat(listOf(1, 2, 3) as Iterable<Int>).containsSublist(3, 4)
        }
        assertEquals(
            """expected to contain sublist:<[3, 4]> but was:<[1, 2, 3]>
              | first missing element:<4>
            """.trimMargin().lines(), error.message!!.lines()
        )
    }
    //endregion

    //region each
    @Test fun each_empty_list_passes() {
        assertThat(emptyList<Int>() as Iterable<Int>).each { it.isEqualTo(1) }
    }

    @Test fun each_content_passes() {
        assertThat(listOf(1, 2) as Iterable<Int>).each { it.isGreaterThan(0) }
    }

    @Test fun each_non_matching_content_fails() {
        val error = assertFails {
            assertThat(listOf(1, 2, 3) as Iterable<Int>).each { it.isLessThan(2) }
        }
        assertEquals(
            """The following assertions failed (2 failures)
              |${"\t"}${opentestPackageName}AssertionFailedError: expected [[1]] to be less than:<2> but was:<2> ([1, 2, 3])
              |${"\t"}${opentestPackageName}AssertionFailedError: expected [[2]] to be less than:<2> but was:<3> ([1, 2, 3])
            """.trimMargin().lines(), error.message!!.lines()
        )
    }
    //endregion

    //region none
    @Test fun none_empty_list_passes() {
        assertThat(emptyList<Int>() as Iterable<Int>).none { it.isEqualTo(1) }
    }

    @Test fun none_matching_content_fails() {
        val error = assertFails {
            assertThat(listOf(1, 2) as Iterable<Int>).none { it.isGreaterThan(0) }
        }
        assertEquals(
            "expected none to pass", error.message
        )
    }

    @Test fun each_non_matching_content_passes() {
        assertThat(listOf(1, 2, 3) as Iterable<Int>).none { it.isLessThan(2) }
    }
    //endregion

    //region atLeast
    @Test fun atLeast_too_many_failures_fails() {
        val error = assertFails {
            assertThat(listOf(1, 2, 3) as Iterable<Int>).atLeast(2) { it.isGreaterThan(2) }
        }
        assertEquals(
            """expected to pass at least 2 times (2 failures)
            |${"\t"}${opentestPackageName}AssertionFailedError: expected [[0]] to be greater than:<2> but was:<1> ([1, 2, 3])
            |${"\t"}${opentestPackageName}AssertionFailedError: expected [[1]] to be greater than:<2> but was:<2> ([1, 2, 3])
        """.trimMargin().lines(), error.message!!.lines()
        )
    }

    @Test fun atLeast_no_failures_passes() {
        assertThat(listOf(1, 2, 3) as Iterable<Int>).atLeast(2) { it.isGreaterThan(0) }
    }

    @Test fun atLeast_less_than_times_failures_passes() {
        assertThat(listOf(1, 2, 3) as Iterable<Int>).atLeast(2) { it.isGreaterThan(1) }
    }

    @Test fun atLeast_works_in_a_soft_assert_context() {
        assertThat(listOf(1, 2, 3) as Iterable<Int>).all { atLeast(2) { it.isGreaterThan(1) } }
    }
    //endregion

    //region atMost
    @Test fun atMost_more_than_times_passed_fails() {
        val error = assertFails {
            assertThat(listOf(1, 2, 3) as Iterable<Int>).atMost(2) { it.isGreaterThan(0) }
        }
        assertEquals(
            """expected to pass at most 2 times""".trimMargin(), error.message
        )
    }

    @Test fun atMost_exactly_times_passed_passes() {
        assertThat(listOf(1, 2, 3) as Iterable<Int>).atMost(2) { it.isGreaterThan(1) }
    }


    @Test fun atMost_less_than_times_passed_passes() {
        assertThat(listOf(1, 2) as Iterable<Int>).atMost(2) { it.isGreaterThan(1) }
    }
    //endregion


    //region exactly
    @Test fun exactly_too_few_passes_fails() {
        val error = assertFails {
            assertThat(listOf(1, 2, 3) as Iterable<Int>).exactly(2) { it.isGreaterThan(2) }
        }
        assertEquals(
            """expected to pass exactly 2 times (2 failures)
            |${"\t"}${opentestPackageName}AssertionFailedError: expected [[0]] to be greater than:<2> but was:<1> ([1, 2, 3])
            |${"\t"}${opentestPackageName}AssertionFailedError: expected [[1]] to be greater than:<2> but was:<2> ([1, 2, 3])
            """.trimMargin().lines(), error.message!!.lines()
        )
    }

    @Test fun exactly_too_many_passes_fails() {
        val error = assertFails {
            assertThat(listOf(5, 4, 3) as Iterable<Int>).exactly(2) { it.isGreaterThan(2) }
        }
        assertEquals(
            """expected to pass exactly 2 times""".trimMargin(), error.message
        )
    }

    @Test fun exactly_too_few_inside_all_fails() {
        val error = assertFails {
            assertThat(listOf(5, 4, 3) as Iterable<Int>).all {
                exactly(2) { it.isGreaterThan(2) }
            }
        }
        assertEquals(
            """expected to pass exactly 2 times""".trimMargin(), error.message
        )
    }

    @Test fun exactly_times_passed_passes() {
        assertThat(listOf(0, 1, 2) as Iterable<Int>).exactly(2) { it.isGreaterThan(0) }
    }
    //endregion

    //region any
    @Test fun any_passes_if_one_item_passes() {
        assertThat(listOf(1, 2) as Iterable<Int>).any { it.isGreaterThan(1) }
    }

    @Test fun any_fails_if_all_fail() {
        val error = assertFails {
            assertThat(listOf(1, 2)).any { it.isGreaterThan(3) }
        }
        assertEquals(
            """expected any item to pass (2 failures)
	        |${"\t"}${opentestPackageName}AssertionFailedError: expected [[0]] to be greater than:<3> but was:<1> ([1, 2])
	        |${"\t"}${opentestPackageName}AssertionFailedError: expected [[1]] to be greater than:<3> but was:<2> ([1, 2])
            """.trimMargin().lines(), error.message!!.lines()
        )
    }
    //endregion

    //region isEmpty
    @Test fun empty_iterable_passes_is_empty() {
        val empty: List<Int> = emptyList()
        assertThat(empty as Iterable<Int>).isEmpty()
    }

    @Test fun non_empty_iterable_fails_is_empty() {
        val nonEmpty: List<Int> = listOf(1)
        val error = assertFails {
            assertThat(nonEmpty as Iterable<Int>).isEmpty()
        }
        assertEquals("expected to be empty but was:<[1]>", error.message)
    }
    //endregion

    //region isNotEmpty
    @Test fun non_empty_iterable_passes_is_not_empty() {
        val nonEmpty: List<Int> = listOf(1)
        assertThat(nonEmpty as Iterable<Int>).isNotEmpty()
    }

    @Test fun empty_iterable_fails_is_not_empty() {
        val empty: List<Int> = emptyList()
        val error = assertFails {
            assertThat(empty as Iterable<Int>).isNotEmpty()
        }
        assertEquals("expected to not be empty", error.message)
    }
    //endregion

    //region extracting
    @Test fun single_extracting_function_passes() {
        assertThat(listOf("one", "two") as Iterable<String>).extracting { it.length }.containsExactly(3, 3)
    }

    @Test fun single_extracting_function_fails() {
        val error = assertFails {
            assertThat(listOf("one", "two") as Iterable<String>).extracting { it.length }.containsExactly(2, 2)
        }
        assertEquals(
            """expected to contain exactly:<[2, 2]> but was:<[3, 3]>
            | at index:0 expected:<2>
            | at index:0 unexpected:<3>
            | at index:1 expected:<2>
            | at index:1 unexpected:<3> (["one", "two"])""".trimMargin(), error.message
        )
    }

    @Test fun pair_extracting_function_passes() {
        assertThat(listOf(Thing("one", 1, '1'), Thing("two", 2, '2')) as Iterable<Thing>)
            .extracting(Thing::one, Thing::two)
            .containsExactly("one" to 1, "two" to 2)
    }

    @Test fun pair_extracting_function_fails() {
        val error = assertFails {
            assertThat(listOf(Thing("one", 1, '1'), Thing("two", 2, '2')) as Iterable<Thing>)
                .extracting(Thing::one, Thing::two)
                .containsExactly("one" to 2, "two" to 1)
        }
        assertEquals(
            """expected to contain exactly:<[("one", 2), ("two", 1)]> but was:<[("one", 1), ("two", 2)]>
            | at index:0 expected:<("one", 2)>
            | at index:0 unexpected:<("one", 1)>
            | at index:1 expected:<("two", 1)>
            | at index:1 unexpected:<("two", 2)> ([Thing(one=one, two=1, three=1), Thing(one=two, two=2, three=2)])""".trimMargin(),
            error.message
        )
    }

    @Test fun triple_extracting_function_passes() {
        assertThat(listOf(Thing("one", 1, '1'), Thing("two", 2, '2')) as Iterable<Thing>)
            .extracting(Thing::one, Thing::two, Thing::three)
            .containsExactly(Triple("one", 1, '1'), Triple("two", 2, '2'))
    }

    @Test fun triple_extracting_function_fails() {
        val error = assertFails {
            assertThat(listOf(Thing("one", 1, '1'), Thing("two", 2, '2')) as Iterable<Thing>)
                .extracting(Thing::one, Thing::two, Thing::three)
                .containsExactly(Triple("one", 1, '2'), Triple("two", 2, '3'))
        }
        assertEquals(
            """expected to contain exactly:<[("one", 1, '2'), ("two", 2, '3')]> but was:<[("one", 1, '1'), ("two", 2, '2')]>
            | at index:0 expected:<("one", 1, '2')>
            | at index:0 unexpected:<("one", 1, '1')>
            | at index:1 expected:<("two", 2, '3')>
            | at index:1 unexpected:<("two", 2, '2')> ([Thing(one=one, two=1, three=1), Thing(one=two, two=2, three=2)])""".trimMargin(),
            error.message
        )
    }
    //region extracting

    data class Thing(val one: String, val two: Int, val three: Char)
}
