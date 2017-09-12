# Introduction

This API allows developers to quickly and easily integrate printing for receipt printers into your
Android application. The API allows you to print using any AEVI enabled device and selected printer
drivers. Specifically this API is designed for use with receipt/line printer type devices.

The print API itself makes extensive use of reactive (Rx) based principles. Therefore in the case
of the Java API it makes heavy use of the RxJava library. To read more about Rx principles and the
RxJava library itself see [the documentation here](https://github.com/ReactiveX/RxJava).
For the remainder of this documentation it is assumed that the reader is familiar with asynchronous
and event-based programming using observable streams.

# Prerequisites

This API is an entry point to the AEVI Printing Service. In order to use this API sucessfully on a
device the printing service application must be installed along with printer driver applications
that will handle the actual print process with the physical devices.

# Binaries

Currently this API is under development and is therefore only published to our own bintray repository.
When we release v1 of this API we will upload it to jcenter.

Therefore, in your main gradle.build you'll need to include our public bintray in your main
repositories section.

```
    repositories {
        maven {
            url "http://dl.bintray.com/aevi/aevi-uk"
        }
    }
```

And then add to your dependencies section

```
compile 'com.aevi.print:print-api:0.0.5'

```

# Sample usage

The main entry point to the SDK is to first obtain an instance of the `PrintManager`. This object
can then be used to send print jobs, actions and listen to printer events.

> To get an instance of the PrintManager within your application

```java

      PrinterManager printerManager = PrinterApi.getPrinterManager(this);

```

In order to bind to the printer service your application must also request the
permission `com.aevi.permission.NGS_PRINT_SERVICE`

```xml
<uses-permission android:name="com.aevi.permission.NGS_PRINT_SERVICE"/>
```

The `PrinterManager` can then be used as shown below to send a print job to a printer. If no printer
is specified then the print will be sent to the default printer. You can specify a printer by setting
the printerId in the `PrintPayload` object. PrinterIds are obtained via the `PrinterManager` using the
`getPrinterSettings()` method.

```java

if(printerManager.isPrinterServiceAvailable()) {

  PrintPayload payload = new PrintPayload();

  // fill out payload here. Described below

  printerManager.print(payload)
          .subscribe(new Consumer<PrintJob>() {
              @Override
              public void accept(@NonNull PrintJob printResult) throws Exception {
                  // Do something with results here
                  Log.d(TAG, "Got status from printer: " + printResult.getPrintJobState());
              }
          }, new Consumer<Throwable>() {
              @Override
              public void accept(@NonNull Throwable throwable) throws Exception {
                  Log.e(TAG, "Error while printing", throwable);
              }
          });
}

```

To read more details about how to use the API and how to create more detailed `PrintPayload` objects
[see our documentation here](https://aevi-uk.github.io/android-pos-print-api)

# Bugs and Feedback

For bugs, feature requests and discussion please use [GitHub Issues](https://github.com/Aevi-UK/android-pos-print-api/issues)

# LICENSE

Copyright 2017 AEVI International GmbH

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
