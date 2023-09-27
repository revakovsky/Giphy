package com.revakovskyi.giphy.presentation.ui.theme

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Phone_1_bigScreen",
    group = "Devices",
    device = "id:pixel_5",
    showSystemUi = true,
    showBackground = true,
)
@Preview(
    name = "Phone_2_smallScreen",
    group = "Devices",
    device = Devices.PIXEL_2,
    showSystemUi = true,
    showBackground = true
)
@Preview(
    name = "Tablet_1_Vertical",
    group = "Devices",
    device = "spec:width=800dp,height=1280dp,dpi=240",
    showSystemUi = true,
    showBackground = true,
)
@Preview(
    name = "Tablet_2_Horizontal",
    group = "Devices",
    device = Devices.TABLET,
    showSystemUi = true,
    showBackground = true,
)
annotation class DevicePreviews
