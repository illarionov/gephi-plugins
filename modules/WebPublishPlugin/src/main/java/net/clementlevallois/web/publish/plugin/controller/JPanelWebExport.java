/*
 * author: Clément Levallois
 */
package net.clementlevallois.web.publish.plugin.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Color;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.prefs.Preferences;
import javax.swing.JLabel;

import javax.swing.JTextField;
import javax.swing.SwingWorker;
import static net.clementlevallois.web.publish.plugin.controller.GlobalConfigParams.*;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.util.NbPreferences;

/**
 *
 * @author LEVALLOIS
 *
 * The logic of this plugin follows the logic laid out here:
 * https://github.com/gephi/gephi-plugins/issues/262#issuecomment-1231627948
 *
 */
public class JPanelWebExport extends javax.swing.JPanel {

    private JsonObject responseGithubConnectAction;
    private JsonObject responseGithubUserCodeInput;
    private String accessToken;
    private String deviceCode;

    private static final ResourceBundle bundle = NbBundle.getBundle(GephiPluginDesktopLogic.class);

    public static final String COLOR_SUCCESS = "#45ba48";

    public JPanelWebExport() {
        initComponents();
        Preferences preferences = NbPreferences.forModule(this.getClass());
        accessToken = preferences.get(ACCESS_TOKEN_KEY_IN_USER_PREFS, "");
        if (accessToken == null || accessToken.isBlank()) {
            jLabelAlreadyLoggedIn.setVisible(false);
        } else {
            jLabelAlreadyLoggedIn.setVisible(true);
        }
        jTextFieldGithubErrorMsg.setBackground(Color.WHITE);
        jTextFieldGithubErrorMsg.setText(bundle.getString("general.message.errors_appear.here"));
        jTextFieldGithubErrorMsg.setCaretPosition(0);
        jTextFieldUserCode.setForeground(Color.RED);
        jTextAreaUrls.setText("");
    }

    SwingWorker pollWorker = new SwingWorker<Void, Integer>() {

        @Override
        protected Void doInBackground() throws Exception {
            JsonObject jsonObject = new JsonObject();
            try {
                HttpClient client = HttpClient.newHttpClient();
                String url = "https://github.com/login/oauth/access_token";

                String inputParams = "client_id="
                        + GEPHI_APP_CLIENT_ID
                        + "&"
                        + "device_code="
                        + deviceCode
                        + "&"
                        + "grant_type=urn:ietf:params:oauth:grant-type:device_code";
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("accept", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(inputParams))
                        .build();

                boolean success = false;
                long startTime = System.currentTimeMillis();
                long maxDuration = 900_000;
                float currDuration = 0;
                int loops = 0;
                while (!success && currDuration < maxDuration) {
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    JsonElement responseAsJsonElement = JsonParser.parseString(response.body());
                    jsonObject = responseAsJsonElement.getAsJsonObject();
                    if (jsonObject.has("access_token")) {
                        break;
                    }
                    currDuration = (float) (System.currentTimeMillis() - startTime) / (float) 1000;
                    // the min duration recommended by Github between two polls is 5 seconds
                    // -> https://docs.github.com/en/developers/apps/building-oauth-apps/authorizing-oauth-apps#device-flow
                    Thread.sleep(5200);
                    publish(++loops);
                }
            } catch (IOException | InterruptedException ex) {
                Exceptions.printStackTrace(ex);
                jTextFieldGithubErrorMsg.setText(ex.getMessage());
                jTextFieldGithubErrorMsg.setCaretPosition(0);
            }
            responseGithubUserCodeInput = jsonObject;
            return null;
        }

        @Override
        public void done() {
            if (responseGithubUserCodeInput.has("access_token")) {
                accessToken = responseGithubUserCodeInput.get("access_token").getAsString();
                Preferences preferences = NbPreferences.forModule(this.getClass());
                preferences.put(ACCESS_TOKEN_KEY_IN_USER_PREFS, accessToken);
                jTextFieldGithubErrorMsg.setForeground(Color.decode(COLOR_SUCCESS));
                jTextFieldGithubErrorMsg.setText(bundle.getString("general.message.success_switch_to_publish"));
                jTextFieldGithubErrorMsg.setCaretPosition(0);
            } else {
                jTextFieldGithubErrorMsg.setText(bundle.getString("general.message.error.no_user_code"));
                jTextFieldGithubErrorMsg.setCaretPosition(0);
            }
        }

        protected void process(Integer loops) {
            jTextFieldGithubErrorMsg.setText(loops.toString());
        }

    };

    public JTextField getjTextFieldUserCode() {
        return jTextFieldUserCode;
    }

    public JsonObject getResponseGithubConnectAction() {
        return responseGithubConnectAction;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabs = new javax.swing.JTabbedPane();
        tabGithub = new javax.swing.JPanel();
        jLabelAlreadyLoggedIn = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButtonConnectToGephiLite = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldUserCode = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jTextFieldWebsiteLoginUrl = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jTextFieldGithubErrorMsg = new javax.swing.JTextField();
        jButtonResetLogin = new javax.swing.JButton();
        tabPublish = new javax.swing.JPanel();
        jButtonPublish = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaUrls = new javax.swing.JTextArea();

        tabs.setMinimumSize(new java.awt.Dimension(700, 454));
        tabs.setPreferredSize(new java.awt.Dimension(700, 454));

        jLabelAlreadyLoggedIn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelAlreadyLoggedIn.setForeground(new java.awt.Color(0, 204, 102));
        org.openide.awt.Mnemonics.setLocalizedText(jLabelAlreadyLoggedIn, "<html>"+bundle.getString("general.message.warning_setup_already_done")+"</html>");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(JPanelWebExport.class, "JPanelWebExport.step1.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, "<html>"+bundle.getString("general.message.github.create_account")+"</html>");

        jTextField1.setText(org.openide.util.NbBundle.getMessage(JPanelWebExport.class, "JPanelWebExport.jTextField1.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(16, 16, 16))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(JPanelWebExport.class, "JPanelWebExport.step3.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(JPanelWebExport.class, "JPanelWebExport.step3.info1")); // NOI18N

        jTextField2.setText(org.openide.util.NbBundle.getMessage(JPanelWebExport.class, "JPanelWebExport.jTextField2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(JPanelWebExport.class, "JPanelWebExport.step3.info2")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(JPanelWebExport.class, "JPanelWebExport.step4.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButtonConnectToGephiLite, org.openide.util.NbBundle.getMessage(JPanelWebExport.class, "JPanelWebExport.jButtonConnectToGephiLite.text")); // NOI18N
        jButtonConnectToGephiLite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConnectToGephiLiteActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, bundle.getString("general.message.then_wait_for_code")+ " --> " 
        );

        jTextFieldUserCode.setForeground(new java.awt.Color(255, 0, 0));
        jTextFieldUserCode.setText(org.openide.util.NbBundle.getMessage(JPanelWebExport.class, "JPanelWebExport.jTextFieldUserCode.text")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonConnectToGephiLite)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextFieldUserCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonConnectToGephiLite)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldUserCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(JPanelWebExport.class, "JPanelWebExport.step6.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jTextFieldWebsiteLoginUrl.setText(org.openide.util.NbBundle.getMessage(JPanelWebExport.class, "JPanelWebExport.jTextFieldWebsiteLoginUrl.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(JPanelWebExport.class, "JPanelWebExport.step6.info1")); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldWebsiteLoginUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldWebsiteLoginUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, "<html>"+bundle.getString("general.message.success_ready_to_publish")+"</html>");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTextFieldGithubErrorMsg.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldGithubErrorMsg)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldGithubErrorMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButtonResetLogin.setBackground(new java.awt.Color(204, 204, 204));
        org.openide.awt.Mnemonics.setLocalizedText(jButtonResetLogin, org.openide.util.NbBundle.getMessage(JPanelWebExport.class, "JPanelWebExport.jButtonResetLogin.text")); // NOI18N
        jButtonResetLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tabGithubLayout = new javax.swing.GroupLayout(tabGithub);
        tabGithub.setLayout(tabGithubLayout);
        tabGithubLayout.setHorizontalGroup(
            tabGithubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabGithubLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabGithubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelAlreadyLoggedIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabGithubLayout.createSequentialGroup()
                        .addGroup(tabGithubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(tabGithubLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButtonResetLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(tabGithubLayout.createSequentialGroup()
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        tabGithubLayout.setVerticalGroup(
            tabGithubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabGithubLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelAlreadyLoggedIn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabGithubLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonResetLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(130, 130, 130))
        );

        tabs.addTab(org.openide.util.NbBundle.getMessage(JPanelWebExport.class, "JPanelWebExport.tabGithub.TabConstraints.tabTitle"), tabGithub); // NOI18N

        jButtonPublish.setBackground(new java.awt.Color(204, 204, 204));
        jButtonPublish.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jButtonPublish, org.openide.util.NbBundle.getMessage(JPanelWebExport.class, "JPanelWebExport.jButtonPublish.text")); // NOI18N
        jButtonPublish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPublishActionPerformed(evt);
            }
        });

        jLabel10.setForeground(new java.awt.Color(255, 51, 51));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel10, "<html>"+bundle.getString("general.message.warning_confidentiality")+"</html>");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel11, "<html>"+bundle.getString("general.message.info.url_will_appear_below")+"</html>");

        jTextAreaUrls.setColumns(20);
        jTextAreaUrls.setRows(5);
        jScrollPane1.setViewportView(jTextAreaUrls);

        javax.swing.GroupLayout tabPublishLayout = new javax.swing.GroupLayout(tabPublish);
        tabPublish.setLayout(tabPublishLayout);
        tabPublishLayout.setHorizontalGroup(
            tabPublishLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabPublishLayout.createSequentialGroup()
                .addGroup(tabPublishLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabPublishLayout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(jButtonPublish, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tabPublishLayout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(158, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabPublishLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(tabPublishLayout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tabPublishLayout.setVerticalGroup(
            tabPublishLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabPublishLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jButtonPublish, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(97, Short.MAX_VALUE))
        );

        tabs.addTab(org.openide.util.NbBundle.getMessage(JPanelWebExport.class, "JPanelWebExport.tabPublish.TabConstraints.tabTitle"), tabPublish); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 26, Short.MAX_VALUE)
                .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 726, Short.MAX_VALUE)
                .addGap(0, 26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonResetLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetLoginActionPerformed
        Preferences preferences = NbPreferences.forModule(this.getClass());
        preferences.remove(ACCESS_TOKEN_KEY_IN_USER_PREFS);
        jLabelAlreadyLoggedIn.setVisible(false);
        jTextFieldGithubErrorMsg.setText(bundle.getString("general.message.success_reset"));
        jTextFieldGithubErrorMsg.setCaretPosition(0);
    }//GEN-LAST:event_jButtonResetLoginActionPerformed

    private void jButtonPublishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPublishActionPerformed
        JsonObject jsonObjectOfGexfAsStringRetrieval = PublishingActions.getGexfAsString();
        if (jsonObjectOfGexfAsStringRetrieval.keySet().size() != 1) {
            jTextAreaUrls.setText(bundle.getString("general.message.error.gexf_not_retrieved"));
            return;
        }
        String key = jsonObjectOfGexfAsStringRetrieval.keySet().iterator().next();

        if (!key.equals(SUCCESS_CODE) & !key.equals(SUCCESS_BUT_WITH_WARNING)) {
            jTextAreaUrls.setText(jsonObjectOfGexfAsStringRetrieval.get(key).getAsString());
            return;
        } else if (key.equals(SUCCESS_BUT_WITH_WARNING)) {
            JLabel warningMessage = new JLabel();
            warningMessage.setText(bundle.getString("general.message.warning.network_too_big"));
            NotifyDescriptor.Confirmation confirmation = new DialogDescriptor.Confirmation(warningMessage, bundle.getString("general.noun.warning"), NotifyDescriptor.WARNING_MESSAGE, NotifyDescriptor.YES_NO_OPTION);
            if (DialogDisplayer.getDefault().notify(confirmation) != NotifyDescriptor.YES_OPTION) {
                return;
            }
        }
        String gexf = jsonObjectOfGexfAsStringRetrieval.get(SUCCESS_CODE).getAsString();
        Preferences preferences = NbPreferences.forModule(this.getClass());
        accessToken = preferences.get(ACCESS_TOKEN_KEY_IN_USER_PREFS, "");
        if (accessToken == null || accessToken.isBlank()) {
            jTextAreaUrls.setText(bundle.getString("general.message.error.no_token"));
        } else {
            String fileName = "network-" + UUID.randomUUID().toString().substring(0, 12) + ".gexf";

            JsonObject responseGistPublished = PublishingActions.postGexfToGist(gexf, accessToken, fileName);
            if (!responseGistPublished.has("201")) {
                if (responseGistPublished.keySet().isEmpty()) {
                    jTextAreaUrls.setText(bundle.getString("general.message.error.unspecific_error_while_publishing"));
                } else {
                    String errorMsgInBodyKey = responseGistPublished.keySet().iterator().next();
                    if (responseGistPublished.get(errorMsgInBodyKey) != null) {
                        String errorMsgInBodyValue = responseGistPublished.get(errorMsgInBodyKey).getAsString();
                        jTextAreaUrls.setText(
                                bundle.getString("general.message.error.gist_creation")
                                + errorMsgInBodyKey
                                + "; "
                                + bundle.getString("general.message.error_message")
                                + errorMsgInBodyValue);
                    }
                }
            } else {
                JsonObject metadataOnGist = responseGistPublished.get("201").getAsJsonObject();
                String htmlUrl = metadataOnGist.get("html_url").getAsString();
                JsonObject metadataOnFiles = metadataOnGist.get("files").getAsJsonObject();
                JsonObject metadataOnOneFile = metadataOnFiles.get(fileName).getAsJsonObject();
                String rawUrl = metadataOnOneFile.get("raw_url").getAsString();
                String retinaFullURl = RETINA_BASE_URL + "?url=" + rawUrl;

                String textForUserWithURL = bundle.getString("general.message.url_published_gexf")
                        + "\n"
                        + htmlUrl
                        + "\n\n"
                        + bundle.getString("general.message.url_published_on_retina")
                        + "\n"
                        + retinaFullURl;

                jTextAreaUrls.setText(textForUserWithURL);
                jTextAreaUrls.setCaretPosition(0);
            }
        }

    }//GEN-LAST:event_jButtonPublishActionPerformed

    private void jButtonConnectToGephiLiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConnectToGephiLiteActionPerformed
        jTextFieldGithubErrorMsg.setBackground(Color.WHITE);
        jTextFieldGithubErrorMsg.setText("");
        responseGithubConnectAction = PublishingActions.connectToGithub();
        if (!responseGithubConnectAction.has("user_code")) {
            jTextFieldUserCode.setForeground(Color.RED);
            jTextFieldGithubErrorMsg.setText(bundle.getString("general.message.error.cant_retrieve_user_code"));
            jTextFieldGithubErrorMsg.setCaretPosition(0);
        } else {
            String userCode = responseGithubConnectAction.get("user_code").getAsString();
            deviceCode = responseGithubConnectAction.get("device_code").getAsString();
            jTextFieldUserCode.setForeground(Color.decode(COLOR_SUCCESS));
            jTextFieldUserCode.setText(userCode);
            pollWorker.execute();
        }
    }//GEN-LAST:event_jButtonConnectToGephiLiteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonConnectToGephiLite;
    private javax.swing.JButton jButtonPublish;
    private javax.swing.JButton jButtonResetLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelAlreadyLoggedIn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaUrls;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextFieldGithubErrorMsg;
    private javax.swing.JTextField jTextFieldUserCode;
    private javax.swing.JTextField jTextFieldWebsiteLoginUrl;
    private javax.swing.JPanel tabGithub;
    private javax.swing.JPanel tabPublish;
    private javax.swing.JTabbedPane tabs;
    // End of variables declaration//GEN-END:variables
}
