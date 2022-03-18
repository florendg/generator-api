package dev.vultureweb.generator.impl;

import dev.vultureweb.generator.domain.Element;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListGeneratorTest {

   @Test
   void aListWithTheSizeOfTheGivenNumberShouldBeGenerated() {
      List<Element> result = new ListGenerator().generateListOf(10);
      assertEquals(10, result.size(),"Expect 10 elements");
   }

}