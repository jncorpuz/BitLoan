package com.example.loditech.bitloan.Data;

import com.example.loditech.bitloan.Models.Account;
import com.example.loditech.bitloan.Models.LoanPayment;
import com.example.loditech.bitloan.Models.Loans;
import com.example.loditech.bitloan.Models.MerchantAccount;
import com.example.loditech.bitloan.Models.Transaction;
import com.example.loditech.bitloan.Models.User;
import com.example.loditech.bitloan.Models.Voucher;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {
    String BASE_URL ="https://bitloan.azurewebsites.net/api/";

    @POST("Account/LoginClient")
    Call<User> LoginUser(@Query("username") String username, @Query("password") String password);

    @POST("Account/Register/")
    Call<Boolean> RegisterUser(@Body Account account);

    @GET("Account/CheckExisting")
    Call<Boolean> CheckExisting(@Query("username") String username);

    @GET("Account/ChangeEmail")
    Call<Boolean> ChangeEmail(@Query("ID") int ID, @Query("password") String password,@Query("emailAddress") String emailAddress);

    @GET("Account/ChangePassword")
    Call<Boolean> ChangePassword(@Query("ID") int ID, @Query("password") String password,@Query("newPassword") String newPassword);

    @POST("Account/UpdateInformation")
    Call<Boolean> UpdateInformation(@Body Account account);

    @GET("Transaction/GetTransaction")
    Call<Transaction> GetTransaction(@Query("refernceID") String referenceID);

    @POST("Transaction/SendMoney")
    Call<Transaction> SendMoney(@Query("senderID") int senderID, @Query("referenceID") String referenceID);

    @GET("Account/GetMerchantAccount")
    Call<MerchantAccount> GetMerchantAccount(@Query("ID") int ID);

    @GET("Transaction/GetAllTransactionsByAccountID")
    Call<List<Transaction>> GetAllTransactionsByAccountID(@Query("accountID") int accountID);

    @POST("Loan/ApplyLoan")
    Call<Boolean> ApplyLoan(@Body Loans entity);

    @GET("Loan/GetBillsPaid")
    Call<List<LoanPayment>> GetBillsPaid(@Query("accountID") int accountID);

    @GET("Loan/GetBillsDue")
    Call<List<LoanPayment>> GetBillsDue(@Query("accountID") int accountID);

    @GET("Voucher/GetAllNotRedeemed")
    Call<List<Voucher>> GetAllNotRedeemed(@Query("accountID") int accountID);

    @GET("Voucher/RedeemVoucher")
    Call<Boolean> RedeemVoucher(@Query("accountID") int accountID, @Query("voucherID") int voucherID);

    @GET("Account/GetUser")
    Call<User> GetUser(@Query("accountID") int accountID);

}
