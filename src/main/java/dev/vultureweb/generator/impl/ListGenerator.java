package dev.vultureweb.generator.impl;

import dev.vultureweb.generator.domain.Element;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ListGenerator {

   public List<Element> generateListOf(int numberOfElements) {
      Element[] elements = new Element[numberOfElements];
      for (int index = 0; index < numberOfElements; index++) {
         elements[index] = new Element(index, UUID.randomUUID().toString(),"an element");
      }
      return List.copyOf(Arrays.stream(elements).toList());
   }
}
