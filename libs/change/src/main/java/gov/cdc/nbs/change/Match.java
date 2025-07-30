package gov.cdc.nbs.change;

public sealed interface Match<L, R> {

  static <L, R> Match<L, R> of(L left, R right) {
    if (left != null && right != null) {
      return new Both<>(left, right);
    } else if (left == null && right != null) {
      return new OnlyRight<>(right);
    } else if (left != null) {
      return new OnlyLeft<>(left);
    } else {
      throw new IllegalStateException("Either a left or a right value is required.");
    }
  }

  record OnlyRight<L, R>(R right) implements Match<L, R> {

  }


  record OnlyLeft<L, R>(L left) implements Match<L, R> {


  }


  record Both<L, R>(L left, R right) implements Match<L, R> {


  }

}
