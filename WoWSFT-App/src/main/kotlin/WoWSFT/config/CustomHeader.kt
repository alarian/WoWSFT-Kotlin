package WoWSFT.config

import WoWSFT.model.Constant.CDN

class CustomHeader {
    companion object {
        private const val headerSrc = "'self' $CDN"
        private const val googleSrc =
            "https://tagmanager.google.com/ " +
            "https://www.googletagmanager.com/ " +
            "https://www.gstatic.com/ " +
            "fonts.googleapis.com/ " +
            "https://www.google-analytics.com/ "

//        private const val headerUnsafe = "";
        private const val headerUnsafe = "'unsafe-inline'"
        private const val none = "'none'"

        const val contentSecurityPolicy =
            "default-src $none;" +
            "object-src $none;" +
            "connect-src 'self';" +
            "base-uri 'self';" +
            "img-src $headerSrc https://ssl.gstatic.com/ https://www.google-analytics.com/;" +
            "script-src $headerUnsafe $headerSrc $googleSrc data:;" +
            "style-src $headerUnsafe $headerSrc $googleSrc;" +
            "font-src https://tagmanager.google.com/ https://fonts.gstatic.com/;" +
            "form-action $none; frame-ancestors $none"
    }
}