package entity;


public class PropertyFee implements Entity{

  private long id;
  private long householdId;

  public PropertyFee(long id, long householdId) {
    this.id = id;
    this.householdId = householdId;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getHouseholdId() {
    return householdId;
  }

  public void setHouseholdId(long householdId) {
    this.householdId = householdId;
  }

}
