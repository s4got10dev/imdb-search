package s4got10dev.imdb.api.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import static java.lang.String.format;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class ActorTypecastDTO {
   private boolean typecast;
   private String message;

   private static final String TYPECASTED_TEMPLATE = "Actor %s is typecast in %s genre";
   private static final String NOT_TYPECASTED_TEMPLATE = "Actor %s is not typecast";

   public static ActorTypecastDTO actorIsTypecasted(String actorName, String genreName) {
      return new ActorTypecastDTO(true, String.format(TYPECASTED_TEMPLATE, actorName, genreName));
   }

   public static ActorTypecastDTO actorIsNotTypecasted(String actorName) {
      return new ActorTypecastDTO(false, String.format(NOT_TYPECASTED_TEMPLATE, actorName));
   }

}
