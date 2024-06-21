package Pojo;

public class TourCompany {
    private int id; // AI PK
    private int userId; // user_id
    private String companyName; // company_name
    private String documents; // documents
    private String cnic; // cnic
    private String status; // status
    private int registrationNumber; // registration_number
    private String accountNumber; // account_number
    private String bankName; // bank_name
    private String accountHolder; // account_holder

    // Default constructor
    public TourCompany() {
    }

    // Parameterized constructor
    public TourCompany(int id, int userId, String companyName, String documents, String cnic, String status, int registrationNumber, String accountNumber, String bankName, String accountHolder) {
        this.id = id;
        this.userId = userId;
        this.companyName = companyName;
        this.documents = documents;
        this.cnic = cnic;
        this.status = status;
        this.registrationNumber = registrationNumber;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.accountHolder = accountHolder;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getDocuments() { return documents; }
    public void setDocuments(String documents) { this.documents = documents; }

    public String getCnic() { return cnic; }
    public void setCnic(String cnic) { this.cnic = cnic; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(int registrationNumber) { this.registrationNumber = registrationNumber; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getAccountHolder() { return accountHolder; }
    public void setAccountHolder(String accountHolder) { this.accountHolder = accountHolder; }

    // toString method
    @Override
    public String toString() {
        return "TourCompany{" +
                "id=" + id +
                ", userId=" + userId +
                ", companyName='" + companyName + '\'' +
                ", documents='" + documents + '\'' +
                ", cnic='" + cnic + '\'' +
                ", status='" + status + '\'' +
                ", registrationNumber=" + registrationNumber +
                ", accountNumber='" + accountNumber + '\'' +
                ", bankName='" + bankName + '\'' +
                ", accountHolder='" + accountHolder + '\'' +
                '}';
    }
}
