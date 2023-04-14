public class Person {
    private String firstName;
    private String lastName;
    private String birthDate;
    private String street;
    private String city;
    private String province;
    private String postalCode;
    private String country;
    private double latitude;
    private double longitude;
    private boolean isNaughty;
    private String dateCreated;

    public Person(String firstName, String lastName, String birthDate,
                  String street, String city, String province, String postalCode,
                  String country, double latitude, double longitude, boolean isNaughty,
                  String dateCreated) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.street = street;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isNaughty = isNaughty;
        this.dateCreated = dateCreated;
    }

    public static final Person[] PEOPLE = {
            new Person("Dillon", "Rux", "1989-06-19", "Avenue Road", "Vancouver", "British Columbia", "VJ8 6D9", "Canada", 59.6, 194.4, false, "2018-11-05"),
            new Person("Cole", "Mineo", "1999-09-04", "Baker Street", "Calgary", "Alberta", "A3B L2P", "Canada", 953, 19.2, true, "2018-10-26"),
            new Person("Ellen", "Brisbin", "1995-02-29", "Broad Street", "Burnaby", "British Columbia", "N5M I2K", "Canada", -34, 253.4, true, "2017-07-29"),
            new Person("Tia", "Purdom", "2010-12-01", "Compton Street", "Toronto", "Ontario", "Q0W E9X", "Canada", 542.9, -89.3, false, "2018-11-09"),
            new Person("Christia", "Lonon", "2003-10-13", "Dorville Road", "Winnipeg", "Manitoba", "L1D Z7Y", "Canada", 453.6, 875.1, false, "2013-04-16")
    };

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean getIsNaughty() {
        return isNaughty;
    }

    public void setIsNaughty(boolean isNaughty) {
        this.isNaughty = isNaughty;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
