package com.epam.rd.contactbook;

public class Contact {

    private String contactName;
    private byte addedEmails, addedSocialLinks, addedTels = 0;
    private byte limit4Emails = 3;
    private byte limit4Social = 5;
    private int arrLength;
    private Email[] emails = new Email[limit4Emails];
    private Social[] links = new Social[limit4Social];
    private ContactInfo tel = null;
    private ContactInfo name;

    public Contact(String contactName) {
        this.contactName = contactName.trim();
        name = new NameContactInfo();
        arrLength++;
    }

    public void rename(String newName) {
        if(newName != null && newName.trim() != ""){
            this.contactName = newName.trim();
        }
    }

    public Email addEmail(String localPart, String domain) {
        if(addedEmails == limit4Emails) return null;

        Email regularEmail = new Email(localPart,domain);
        emails[addedEmails++] = regularEmail;
        arrLength++;

        return regularEmail;
    }

    public Email addEpamEmail(String firstname, String lastname) {
        if(addedEmails == limit4Emails) return null;

        Email epamEmail = new Email(firstname+"_"+lastname){
            @Override
            public String getTitle() {
                return "Epam Email";
            }
        };

        emails[addedEmails++] = epamEmail;
        arrLength++;

        return epamEmail;
    }

    public ContactInfo addPhoneNumber(int code, String number) {
        if(tel != null ) return null;

        ContactInfo phone = new ContactInfo() {
            @Override
            public String getTitle() {
                return "Tel";
            }

            @Override
            public String getValue() {
                return "+"+code+" "+number;
            }
        };

        arrLength++;
        return tel = phone;
    }

    public Social addTwitter(String twitterId) {
        return addSocialMedia("Twitter",twitterId);
    }

    public Social addInstagram(String instagramId) {
        return addSocialMedia("Instagram",instagramId);
    }

    public Social addSocialMedia(String title, String id) {
        if(addedSocialLinks == limit4Social){
            return null;
        }

        Social contactLink = new Social(title,id);
        links[addedSocialLinks++] = contactLink;
        arrLength++;

        return contactLink;
    }

    public static class Social implements ContactInfo{
        private String title;
        private String link;

        public Social(String title, String link) {
            this.title = title;
            this.link = link;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getValue() {
            return link;
        }
    }

    private class NameContactInfo implements ContactInfo{

        @Override
        public String getTitle() {
            return "Name";
        }

        @Override
        public String getValue() {
            return contactName;
        }
    }

    public static class Email implements ContactInfo{
        protected String localPart, domain;

        public Email(String localPart, String domain) {
            this.localPart = localPart.trim();
            this.domain = domain.trim();
        }

        public Email(String localPart) {
            this.localPart = localPart;
            this.domain = "epam.com";
        }

        @Override
        public String getTitle() {
            return "Email";
        }

        @Override
        public String getValue() {
            return localPart+"@"+domain;
        }
    }

    public ContactInfo[] getInfo() {
        ContactInfo infoInOrder[] = new ContactInfo[arrLength];
        int index = 0;

        if( name != null ){
            infoInOrder[index++] = name;
        }
        if( tel != null ){
            infoInOrder[index++] = tel;
        }
        if( addedEmails > 0 ){
            for ( int i=0; i<addedEmails; i++ ){
                infoInOrder[index++] = emails[i];
            }
        }
        if( addedSocialLinks > 0 ){
            for( int i=0; i<addedSocialLinks; i++ ){
                infoInOrder[index++] = links[i];
            }
        }
        return infoInOrder;
    }
}
