inherit systemd

SYSTEMD_AUTO_ENABLE = "enable"

RDEPENDS:${PN} += "systemd"

FILES:${PN} += "${sysconfdir}/systemd/system/nvm_daemon.service"
FILES:${PN} += "${systemd_unitdir}/system"
