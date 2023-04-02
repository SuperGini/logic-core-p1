package com.gini.mappers;

public interface Mapper <T,R,U>{

   default T mapFromRequest (R value){
       return null;
   }

   default U mapToResponse(T value){
       return null;
   }

}
