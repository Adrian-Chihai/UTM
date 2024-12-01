    package org.example.subscriber;
    import java.util.List;

    public class SubscriberConfig {
        private String clientId;
        private List<String> subscriptions;

        public SubscriberConfig(String clientId, List<String> subscriptions) {
            this.clientId = clientId;
            this.subscriptions = subscriptions;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public List<String> getSubscriptions() {
            return subscriptions;
        }

        public void setSubscriptions(List<String> subscriptions) {
            this.subscriptions = subscriptions;
        }
    }

