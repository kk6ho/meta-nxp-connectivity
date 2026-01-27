FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://hostapd-easymesh.patch"

do_compile:prepend() {
    echo "CONFIG_NXP_EASYMESH=y" >> ${B}/hostapd/.config
}
