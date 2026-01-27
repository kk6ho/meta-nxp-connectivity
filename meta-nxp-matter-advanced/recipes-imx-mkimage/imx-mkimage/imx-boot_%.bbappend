FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

PATCHTOOL = "git"
SRC_URI += "${@bb.utils.contains_any('MACHINE', "imx8mmevk-matter", 'file://0001-Add-prebuilt-Trusty-binary-for-Matter-for-i.MX8MM.patch', '', d)}"
SRC_URI += "${@bb.utils.contains_any('MACHINE', "imx8mpevk-matter", 'file://0001-Add-prebuilt-Trusty-binary-for-Matter-for-i.MX8MP.patch', '', d)}"
ATF_MACHINE_NAME:append = "${@bb.utils.contains('MACHINE_FEATURES', 'trusty', '-trusty', '', d)}"

DEPLOY_TRUSTY = "${@bb.utils.contains('MACHINE_FEATURES', 'trusty', 'true', 'false', d)}"
