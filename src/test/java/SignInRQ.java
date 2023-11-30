public class SignInRQ {
    private String deviceId;
    private String signUpType;
    private String accessToken;
    private String appleCode;
    private Integer language;
    private String fromIOS;
    private String authorizationCodeZalo;
    private String codeVerifierZalo;
    private String email;
    private String password;


    public SignInRQ() {
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSignUpType() {
        return signUpType;
    }

    public void setSignUpType(String signUpType) {
        this.signUpType = signUpType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAppleCode() {
        return appleCode;
    }

    public void setAppleCode(String appleCode) {
        this.appleCode = appleCode;
    }

    public Integer getLanguage() {
        return language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }

    public String getFromIOS() {
        return fromIOS;
    }

    public void setFromIOS(String fromIOS) {
        this.fromIOS = fromIOS;
    }

    public String getAuthorizationCodeZalo() {
        return authorizationCodeZalo;
    }

    public void setAuthorizationCodeZalo(String authorizationCodeZalo) {
        this.authorizationCodeZalo = authorizationCodeZalo;
    }

    public String getCodeVerifierZalo() {
        return codeVerifierZalo;
    }

    public void setCodeVerifierZalo(String codeVerifierZalo) {
        this.codeVerifierZalo = codeVerifierZalo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
