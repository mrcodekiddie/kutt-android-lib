package com.kutt.it.kutt_android;

public class KuttBuilder
{
   private String domain;
   private String api_key;

   public KuttBuilder setDomain(String domain)
   {
       this.domain = domain;
       return this;
   }

   public KuttBuilder setApi_key(String api_key)
   {
       this.api_key = api_key;
       return this;
   }

   public Kutt build()
   {
       return new Kutt(domain, api_key);
   }
}
