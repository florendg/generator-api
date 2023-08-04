package dev.vultureweb.generator.impl;

import dev.vultureweb.generator.domain.Element;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ListGeneratorBeanTest {

   @Test
   @Disabled
   void aListWithTheSizeOfTheGivenNumberShouldBeGenerated() {
      List<Element> result = new ListGeneratorBean().generateListOf(10);
      assertEquals(10, result.size(),"Expect 10 elements");
   }

   @Test
   @Disabled
   void itemShouldBeOfTypeElement() {
      List<Element> result = new ListGeneratorBean().generateListOf(1);
      assertEquals(Element.class, result.get(0).getClass(),"Expect Element");
   }


}